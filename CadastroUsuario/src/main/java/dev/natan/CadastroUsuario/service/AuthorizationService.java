package dev.natan.CadastroUsuario.service;

import dev.natan.CadastroUsuario.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {



    @Autowired
    UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        return usuariosRepository.findByNome(nome);
    }
}
