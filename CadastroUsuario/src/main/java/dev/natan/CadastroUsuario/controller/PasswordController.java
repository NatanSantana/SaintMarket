package dev.natan.CadastroUsuario.controller;

import dev.natan.CadastroUsuario.entity.PasswordResetToken;
import dev.natan.CadastroUsuario.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reset")
public class PasswordController {

    @Autowired
    private PasswordResetService passwordResetService;



    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> enviarEmail(@RequestParam String email) {
        passwordResetService.criarToken(email);
        return ResponseEntity.ok("Email Enviado");

    }

    @PostMapping("nova-senha")
    public ResponseEntity<String> resetarSenha(@RequestParam String token, @RequestParam String novaSenha) {
        passwordResetService.resetarSenha(token, novaSenha);
        return ResponseEntity.ok("Senha Trocada Com sucesso");

    }



}
