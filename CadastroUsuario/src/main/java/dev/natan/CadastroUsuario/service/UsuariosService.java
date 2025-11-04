package dev.natan.CadastroUsuario.service;

import dev.natan.CadastroUsuario.Repository.UsuariosRepository;
import dev.natan.CadastroUsuario.entity.Usuarios;
import dev.natan.CadastroUsuario.exceptions.entradaIncorretaDeDadosException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }


    public List<Usuarios> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    public Usuarios buscarPorId(Long id) {
        return usuariosRepository.findById(id).orElseThrow(() -> new entradaIncorretaDeDadosException("O ID: "+id+" Não Foi encontrado"));
    }

    public Usuarios salvarUsuarios (Usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }

    public void deletarUsuario(Long id) {
        usuariosRepository.deleteById(id);
    }
}
