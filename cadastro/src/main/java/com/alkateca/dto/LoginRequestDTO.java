package com.alkateca.dto;

public record LoginRequestDTO(
        Long id,
        String username,
        String password,
        String email
) {
}
