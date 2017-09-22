package com.example.user.mipp.Modelo;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;


public class Tela implements Serializable {
    private int codigo;
    private int timer;
    private String imagem;
    private ArrayList<Produto> produtos = new ArrayList<>();

    public Tela(int codigo, int timer, String imagem) {
        setCodigo(codigo);
        setTimer(timer);
        setImagem(imagem);
    }

    private void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getTimer() {
        return timer;
    }

    private void setTimer(int timer) {
        this.timer = timer;
    }

    public int getQtdProdutos() {
        return produtos.size();
    }

    private void setImagem(String imagem) { this.imagem = imagem; }

    public Drawable getImagem() { return Save.decode(imagem);}

    public void addProduto(Produto produto){ produtos.add(produto); }

    public ArrayList<Produto> getProdutos(){ return produtos; }


}
