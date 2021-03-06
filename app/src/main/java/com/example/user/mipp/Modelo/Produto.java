package com.example.user.mipp.Modelo;

import java.io.Serializable;

public class Produto implements Serializable {
    private String cod;
    private String nomeProduto;
    private String preco;
    private boolean promocao;

    public Produto(String cod, String nomeProduto, String preco, boolean promocao){
        setCod(cod);
        setNomeProduto(nomeProduto);
        setPreco(preco);
        setPromocao(promocao);
    }

    public boolean isPromocao() {return promocao;}

    public void setPromocao(boolean promocao) {this.promocao = promocao;}

    public String getCod() {return cod;}

    public void setCod(String cod) {this.cod = cod;}

    public String getNomeProduto() {return nomeProduto;}

    public void setNomeProduto(String nomeProduto) {this.nomeProduto = nomeProduto;}

    public String getPreco() {return preco;}

    public void setPreco(String preco) {this.preco = preco;}

    @Override
    public String toString() {
        return getCod()+ getNomeProduto()+ getPreco();
    }

}
