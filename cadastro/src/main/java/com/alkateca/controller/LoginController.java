package com.alkateca.controller;
import com.alkateca.dto.LoginResponseDTO;
import com.alkateca.models.User;
import com.alkateca.service.LoginService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/api/v1")
public class LoginController {

    @Inject
    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GET
    @Path("/{id}")
    public LoginResponseDTO getUser(@PathParam("id") Long id) {
        User user = loginService.getUser(id);
        return new LoginResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        loginService.createUser(user);
        LoginResponseDTO loginResponse = new LoginResponseDTO(
                                            user.getId(),
                                            user.getUsername(),
                                            user.getEmail());

        return Response.status(Response.Status.CREATED).entity(loginResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        loginService.deleteUser(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
