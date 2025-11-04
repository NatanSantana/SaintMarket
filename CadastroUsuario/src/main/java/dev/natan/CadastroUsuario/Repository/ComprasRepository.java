package dev.natan.CadastroUsuario.Repository;

import dev.natan.CadastroUsuario.entity.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComprasRepository extends JpaRepository<Compras,Long> {

    List<Compras> findAllByCpf(String cpf);
}
