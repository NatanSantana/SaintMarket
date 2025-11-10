package dev.natan.CadastroUsuario.entity;

import dev.natan.CadastroUsuario.roles.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

public record RegisterDTO(String nome, int idade, String email, String senha, String cpf, UserRole role) {
}
