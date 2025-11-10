package dev.natan.CadastroUsuario.service;


import dev.natan.CadastroUsuario.Repository.PasswordResetTokenRepository;
import dev.natan.CadastroUsuario.Repository.UsuariosRepository;
import dev.natan.CadastroUsuario.entity.PasswordResetToken;
import dev.natan.CadastroUsuario.entity.Usuarios;
import dev.natan.CadastroUsuario.exceptions.entradaIncorretaDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;


    public void criarToken (String email) {
        if (usuariosRepository.findByEmail(email) == null) throw new entradaIncorretaDeDadosException("Usuário não encontrado");

        Usuarios user = (Usuarios) usuariosRepository.findByEmail(email);

        String token = UUID.randomUUID().toString();

        PasswordResetToken registrarToken = new PasswordResetToken();
        registrarToken.setToken(token);
        registrarToken.setUsuarios(user);
        registrarToken.setDataExpiracao(LocalDateTime.now().plusMinutes(5));

        passwordResetTokenRepository.save(registrarToken);

        String resetLink = "http://localhost:8080/reset/nova-senha?token=" + token;


        emailService.sendEmail(user.getEmail()
                    , "Redefinir Senha"
                    , "Clique no link para redefinir a senha: " + resetLink);

    }

    public void resetarSenha(String token, String novaSenha) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new entradaIncorretaDeDadosException("Token Inválido"));

        if (resetToken.isExpired()) {
            throw new RuntimeException("Token Expirado");
        }

        Usuarios user = resetToken.getUsuarios();
        user.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
        usuariosRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

    }





}
