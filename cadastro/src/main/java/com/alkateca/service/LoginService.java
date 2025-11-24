package com.alkateca.service;

import com.alkateca.models.User;
import com.alkateca.repository.LoginRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class LoginService {

    @Inject
    LoginRepository loginRepository;

    @Transactional
    public User createUser(User user) {

        if (loginRepository.findByEmail(user.getEmail()) != null) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }

        loginRepository.persist(user);

        return user;

    }

    @Transactional
    public User getUser(Long id) {
        return loginRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Usuário com id " + id + " não encontrado."));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (loginRepository.findById(id) == null) {
            throw new NotFoundException((String) "User not found");
        }
        loginRepository.deleteById(id);

    }

}
