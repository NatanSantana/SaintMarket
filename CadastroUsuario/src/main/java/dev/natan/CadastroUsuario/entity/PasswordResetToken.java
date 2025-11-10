package dev.natan.CadastroUsuario.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_token;

    private String token;

    @OneToOne
    private Usuarios usuarios;

    private LocalDateTime dataExpiracao;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(dataExpiracao);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
}
