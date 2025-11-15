package dev.natan.CadastroUsuario.entity;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Promocoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_promocao;

    private BigDecimal porcentagem;

    private String categoria;

    private LocalDateTime expireDate;


    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireDate);
    }

    public Long getId_promocao() {
        return id_promocao;
    }

    public void setId_promocao(Long id_promocao) {
        this.id_promocao = id_promocao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(BigDecimal porcentagem) {
        this.porcentagem = porcentagem;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}
