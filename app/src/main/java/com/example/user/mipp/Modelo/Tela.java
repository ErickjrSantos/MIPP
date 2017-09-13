package com.example.user.mipp.Modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 01/09/17.
 */

public class Tela implements Serializable {
    int codigo;
    String unidade;
    int timer;
    String imagem;
    ArrayList<Produto> produtos = new ArrayList<>();

    public Tela(int codigo, int timer, String imagem) {
        setCodigo(codigo);
        setTimer(timer);
        setImagem(imagem);

    }

    public int getCodigo() {
        return codigo;
    }

    private void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getUnidade() {
        return unidade;
    }

    private void setUnidade(String unidade) {
        this.unidade = unidade;
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

    public Drawable getImagem() {
        return Save.decode(imagem);}

    public void addProduto(Produto produto){ produtos.add(produto); }

    public ArrayList<Produto> getProdutos(){ return produtos; }


}
