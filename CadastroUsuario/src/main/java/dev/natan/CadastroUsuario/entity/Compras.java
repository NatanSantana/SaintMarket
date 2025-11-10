package dev.natan.CadastroUsuario.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "comprasRealizadas")
public class Compras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_compras;

    private String produto;
    private BigDecimal preco;
    private String dataHora;
    private String cpf;
    private int qtde_produto;


    public int getQtde_produto() {
        return qtde_produto;
    }

    public void setQtde_produto(int qtde_produto) {
        this.qtde_produto = qtde_produto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getId_compras() {
        return id_compras;
    }

    public void setId_compras(Long id_compras) {
        this.id_compras = id_compras;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}
