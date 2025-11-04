package dev.natan.CadastroUsuario.controller;

import dev.natan.CadastroUsuario.Repository.ProdutosRepository;
import dev.natan.CadastroUsuario.entity.Produtos;
import dev.natan.CadastroUsuario.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    private final ProdutoService produtoService;
    private final ProdutosRepository produtosRepository;

    public ProdutosController(ProdutoService produtoService, ProdutosRepository produtosRepository) {
        this.produtoService = produtoService;
        this.produtosRepository = produtosRepository;
    }


    @GetMapping
    public List<Produtos> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @PostMapping("/cadastrar")
    public Produtos cadastrarProduto(@RequestBody Produtos produtos) {
        return produtoService.salvarProduto(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtos> buscarProdutoPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
    }

    @PutMapping("/atualizarEstoque/{idProduto}/{adcEstoque}")
    public Produtos adicionarEstoque(@PathVariable Long idProduto, @PathVariable int adcEstoque ) {

        return produtoService.atualizarEstoque(idProduto, adcEstoque);
    }

}
