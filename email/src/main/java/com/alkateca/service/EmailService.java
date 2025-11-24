package com.alkateca.service;

import com.alkateca.models.User;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EmailService {

    @Inject
    Mailer mailer;

    @Inject
    @RestClient
    UserRestCliente userRestCliente;

    public boolean sendEmail(Long senderId, Long receiverId) {
        User sender;
        User receiver;

        try {
            sender = userRestCliente.getUser(senderId);
            receiver = userRestCliente.getUser(receiverId);
        } catch (NotFoundException e) {
            System.err.println("Erro: Um dos usuários não foi encontrado no Serviço de Cadastro.");
            return false;
        } catch (Exception e) {
            System.err.println("Erro de comunicação com o Serviço de Usuários: " + e.getMessage());
            return false;
        }

        try {
            String subject = "Notificação de Mensagem de " + sender.getUsername();
            String body = String.format(
                    "Olá %s,\n\nVocê recebeu uma notificação do usuário %s (%s).",
                    receiver.getUsername(),
                    sender.getUsername(),
                    sender.getEmail()
            );

            Mail mailMessage = Mail.withText(receiver.getEmail(),subject, body)
                    .setFrom(receiver.getEmail());


            mailer.send(mailMessage);

            System.out.printf("Email enviado para %s (Destinatário: %s) sobre mensagem de %s.%n",
                    receiver.getEmail(), receiver.getUsername(), sender.getUsername());
            return true;

        } catch (Exception e) {
            System.err.println("Falha ao enviar e-mail via Mailer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}