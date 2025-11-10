package dev.natan.CadastroUsuario.controller;

import dev.natan.CadastroUsuario.Repository.UsuariosRepository;
import dev.natan.CadastroUsuario.entity.AuthenticationDTO;
import dev.natan.CadastroUsuario.entity.LoginResponseDTO;
import dev.natan.CadastroUsuario.entity.RegisterDTO;
import dev.natan.CadastroUsuario.entity.Usuarios;
import dev.natan.CadastroUsuario.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.nome(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuarios) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
    if (this.usuariosRepository.findByEmail(data.email()) != null ) throw new IllegalArgumentException("EMAIL JÁ CADASTRADO");
    else if (usuariosRepository.existsByCpf(data.cpf())) throw new IllegalArgumentException("CPF JÁ CADASTRADO");


    String encryptedPassword =  new BCryptPasswordEncoder().encode(data.senha());
    Usuarios novoUsuario = new Usuarios(data.nome(), data.idade(), data.email(), encryptedPassword, data.cpf(), data.role());

    this.usuariosRepository.save(novoUsuario);
    return ResponseEntity.ok().build();

    }

}
