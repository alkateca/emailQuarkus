# ⚙️ README: Sistema de Envio de E-mails (Microservice Email)

Este documento descreve a configuração e o uso do **Serviço de Envio de E-mails**, um microserviço construído com **Quarkus (Java/Jakarta EE)** que se integra a um **Serviço de Usuários** para realizar envios de e-mails personalizados via **SMTP (Gmail)**.

---

# 🚀 Visão Geral da Arquitetura

O Serviço de Email atua como um orquestrador:

- Recebe uma solicitação de envio com IDs de usuário (`senderId` e `receiverId`).
- Utiliza **MicroProfile Rest Client** para se comunicar com o Serviço de Usuários e buscar dados completos do remetente e destinatário.
- Utiliza o **Quarkus Mailer** para enviar e-mails personalizados através de um servidor SMTP externo (configurado via `application.properties`).

---

# ⚙️ Configuração do Ambiente (`application.properties`)

A configuração abaixo é essencial para o funcionamento correto:

```properties
quarkus.http.port=8081

quarkus.mailer.from={E-mail pelo qual serão enviadas as mensagens}
quarkus.mailer.host=smtp.gmail.com
quarkus.mailer.mock=false

quarkus.mailer.username={E-mail de login}
quarkus.mailer.password={Senha de aplicativo do Gmail}

quarkus.mailer.port=465
quarkus.mailer.ssl=true
quarkus.mailer.encryption=SSL
```
## 🔒 Importante: O Gmail exige Senha de Aplicativo, não a senha normal da conta.
> Crie uma em: Google Account → Segurança → Senhas de app.

---

# 📡 Endpoints da API

O serviço de cadastro possui 3 end points:

POST
```
/api/v1/
  application/json

Exemplo de requisição via cULR: curl -X POST 'http://localhost:8080/api/v1' -H 'Content-Type: application/json' -d '{ "username": "", "password": "", "email": "" }'
```
GET
```
api/v1/{id}
    application/json

Exemplo de requisição via cULR: curl -X GET 'http://localhost:8080/api/v1/{id}' -H 'Accept: application/json'
```
DELETE
```
/api/v1/{id}

Exemplo de requisição via cULR: curl -X DELETE 'http://localhost:8080/api/v1/{id}'
```

O serviço de e-mail expõe um único endpoint principal para o envio de e-mails via método POST.

POST
```
/api/v1/email?senderId={id}&receiverId={id}

Exemplo de requisição via cULR: curl -X POST "http://localhost:8081/api/v1/email?senderId={id}&receiverId={id}"
```


