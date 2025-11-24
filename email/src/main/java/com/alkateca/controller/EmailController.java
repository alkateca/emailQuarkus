package com.alkateca.controller;

import com.alkateca.service.EmailService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/email")
public class EmailController {

    @Inject
    EmailService emailService;


    @POST
    public Response sendEmail(
            @QueryParam("senderId") Long senderId,
            @QueryParam("receiverId") Long receiverId) {

        if (senderId == null || receiverId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Os parâmetros senderId e receiverId são obrigatórios.")
                    .build();
        }

        boolean success = emailService.sendEmail(senderId, receiverId);

        if (success) {
            return Response.status(Response.Status.ACCEPTED)
                    .entity("Solicitação de Email para usuários processada.")
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Falha ao enviar e-mail. Verifique se os IDs dos usuários são válidos ou os logs do sistema.")
                    .build();
        }
    }

}