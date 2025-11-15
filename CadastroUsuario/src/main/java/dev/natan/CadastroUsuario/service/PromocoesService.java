package dev.natan.CadastroUsuario.service;

import dev.natan.CadastroUsuario.Repository.PromocoesRepository;
import dev.natan.CadastroUsuario.entity.Promocoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PromocoesService {

    @Autowired
    private PromocoesRepository promocoesRepository;


    public Promocoes lancarPromocao(Promocoes promocoes, int tempoDuracaoHoras) {

        promocoes.setExpireDate(LocalDateTime.now().plusHours(tempoDuracaoHoras));

        return promocoesRepository.save(promocoes);
    }


}
