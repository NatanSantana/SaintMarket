package dev.natan.CadastroUsuario.controller;

import dev.natan.CadastroUsuario.entity.Usuarios;
import dev.natan.CadastroUsuario.exceptions.entradaIncorretaDeDadosException;
import dev.natan.CadastroUsuario.service.UsuariosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuariosService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            Usuarios usuarios = usuariosService.buscarPorId(id);
            return ResponseEntity.ok(usuarios);

        } catch (entradaIncorretaDeDadosException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public Usuarios cadastrarUsuario(@RequestBody Usuarios usuarios) {
        return usuariosService.salvarUsuarios(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuariosService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }




}
