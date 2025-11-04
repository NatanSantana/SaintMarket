package dev.natan.CadastroUsuario.service;

import dev.natan.CadastroUsuario.Repository.ComprasRepository;
import dev.natan.CadastroUsuario.Repository.ProdutosRepository;
import dev.natan.CadastroUsuario.Repository.UsuariosRepository;
import dev.natan.CadastroUsuario.entity.Compras;
import dev.natan.CadastroUsuario.entity.Produtos;
import dev.natan.CadastroUsuario.entity.Usuarios;
import dev.natan.CadastroUsuario.exceptions.GlobalExceptionHandler;
import dev.natan.CadastroUsuario.exceptions.entradaIncorretaDeDadosException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ComprasService {

    private final ComprasRepository comprasRepository;
    private final ProdutosRepository produtosRepository;
    private final ProdutoService produtoService;
    private final UsuariosRepository usuariosRepository;


    public ComprasService(ComprasRepository comprasRepository, ProdutosRepository produtosRepository, ProdutoService produtoService, UsuariosRepository usuariosRepository) {
        this.comprasRepository = comprasRepository;
        this.produtosRepository = produtosRepository;
        this.produtoService = produtoService;
        this.usuariosRepository = usuariosRepository;
    }


    public List<Compras> listarCompras() {
        return comprasRepository.findAll();
    }

    public Compras registrarCompra(Long idProduto, int qtdeProdutos, String cpf) {
        LocalDateTime data = LocalDateTime.now();
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Compras compras = new Compras();

        Optional<Produtos> produtoResgatado = produtosRepository.findById(idProduto);
        Optional<Usuarios> seExiste = usuariosRepository.findByCpf(cpf);



        if (produtoResgatado.isEmpty()) {
            throw new IllegalArgumentException("Produto com ID: " + idProduto + " NÃO EXISTE!");

        } else if (qtdeProdutos <= 0) {
            throw new entradaIncorretaDeDadosException("Não é permitido registrar uma compra " +
                    "com uma quantidade de pedidos igual ou menor que 0");
        }


        int estoqueAtual = produtoResgatado.get().getEstoque();


        if (estoqueAtual == 0) {
            throw new entradaIncorretaDeDadosException("O Estoque Atual é nulo, impossível realizar uma compra");
        } else {

            String nomeProduto = produtoResgatado.get().getNomeProduto();
            BigDecimal precoProduto = produtoResgatado.get().getPreco();


            int estoqueAtualizado = estoqueAtual - qtdeProdutos;
            Optional<Produtos> produtosS = produtoService.buscarProdutoPorId(idProduto);

            produtosS.get().setEstoque(estoqueAtualizado);



            compras.setProduto(nomeProduto);
            compras.setPreco(precoProduto);
            compras.setDataHora(String.valueOf(data.format(formatoBR)));
            compras.setCpf(cpf != null ? cpf : "n/a");
        }

        return comprasRepository.save(compras);
    }

    public List<Compras> listarComprasFeitasPorCpf(String cpf) {

        List<Compras> lista = comprasRepository.findAllByCpf(cpf);
        if (lista.isEmpty()) {
            throw new entradaIncorretaDeDadosException("Não há produtos vinculados ao cpf requisitado");
        } else {
            return lista;
        }
    }

    public void cancelarCompraFeita(Long id) {

        Compras procurarId = comprasRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Essa compra não existe"));

        comprasRepository.deleteById(id);
    }







}
