package dev.natan.CadastroUsuario.service;

import dev.natan.CadastroUsuario.Repository.ProdutosRepository;
import dev.natan.CadastroUsuario.Repository.PromocoesRepository;
import dev.natan.CadastroUsuario.entity.Produtos;
import dev.natan.CadastroUsuario.entity.Promocoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ResgatarPromocaoService {


    private final ProdutosRepository produtosRepository;


    private final PromocoesRepository promocoesRepository;



    public ResgatarPromocaoService(ProdutosRepository produtosRepository, PromocoesRepository promocoesRepository) {
        this.produtosRepository = produtosRepository;
        this.promocoesRepository = promocoesRepository;

    }


    public BigDecimal verificarPromocao(Long idProduto, String categoria) {
        BigDecimal precoOriginal = null;
        BigDecimal totalPromocao = null;

        Optional<Produtos> produtos = produtosRepository.findById(idProduto);
        Promocoes promocao = promocoesRepository.findByCategoria(categoria);

        if (promocao == null) {
            precoOriginal = produtos.get().getPreco();
            return precoOriginal;
        } else {

            if (promocao.isExpired()) {
                promocoesRepository.deleteById(promocao.getId_promocao());
                precoOriginal = produtos.get().getPreco();
                return precoOriginal;
            } else {

                precoOriginal = produtos.get().getPreco();

                BigDecimal porcentagem = promocao.getPorcentagem();

                BigDecimal resultadoMultiplicacao = precoOriginal.multiply(porcentagem);

                totalPromocao = precoOriginal.subtract(resultadoMultiplicacao);
                return totalPromocao;

            }
        }
    }
}
