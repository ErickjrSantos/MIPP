package com.example.user.mipp.Modelo;

import java.io.Serializable;

/**
 * Created by user on 01/09/17.
 */

public class Produto implements Serializable {
    private String cod;
    private String nomeProduto;
    private String preco;




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
