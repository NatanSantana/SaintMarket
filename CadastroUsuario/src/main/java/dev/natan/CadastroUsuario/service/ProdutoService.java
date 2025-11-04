package dev.natan.CadastroUsuario.service;

import dev.natan.CadastroUsuario.Repository.ProdutosRepository;
import dev.natan.CadastroUsuario.entity.Produtos;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutosRepository produtosRepository;


    public ProdutoService(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    public List<Produtos> listarProdutos() {
        return produtosRepository.findAll();
    }

    public Produtos salvarProduto(Produtos produtos) {
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        produtos.setDataHora(String.valueOf(data.format(formatoBR)));
        return produtosRepository.save(produtos);
    }

    public Optional<Produtos> buscarProdutoPorId(Long id) {
        return produtosRepository.findById(id);
    }

    public Produtos atualizarEstoque(Long idProduto, int qtdeAdicionar) {

        Produtos produto = produtosRepository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));;

        int estoqueAtual = produto.getEstoque();
        int novoEstoque = estoqueAtual + qtdeAdicionar;

        produto.setEstoque(novoEstoque);

        return produtosRepository.save(produto);
    }

    public void deletarProduto(Long id) {
        produtosRepository.deleteById(id);
    }




}
