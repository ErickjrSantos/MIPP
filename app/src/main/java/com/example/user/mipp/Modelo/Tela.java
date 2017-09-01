package com.example.user.mipp.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 01/09/17.
 */

public class Tela implements Serializable {
    int codigo;
    String unidade;
    int timer;
    int qtdProdutos;
    public ArrayList<Produto> produtos = new ArrayList<>(1);

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getQtdProdutos() {
        return qtdProdutos;
    }

    public void setQtdProdutos(int qtdProdutos) {
        this.qtdProdutos = qtdProdutos;
    }

}
