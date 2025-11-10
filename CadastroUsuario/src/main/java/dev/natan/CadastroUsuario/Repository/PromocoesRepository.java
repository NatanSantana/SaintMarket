package dev.natan.CadastroUsuario.Repository;

import dev.natan.CadastroUsuario.entity.Promocoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromocoesRepository extends JpaRepository<Promocoes, Long> {

    Optional<Promocoes> findById(Long id);
    Promocoes findByCategoria(String categoria);
}
