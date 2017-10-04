package com.example.user.mipp.Modelo;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;


public class Tela implements Serializable {
    private int codigo;
    private String tipoMidia;
    private int timer;
    private String imagem, corNormal, corPromo;




    private ArrayList<Produto> produtos = new ArrayList<>();

    public Tela(int codigo, int timer,String tipoMidia, String imagem, String corNormal, String corPromo) {
        setCodigo(codigo);
        setTimer(timer);
        setTipoMidia(tipoMidia);
        setImagem(imagem);
        setCorNormal(corNormal);
        setCorPromo(corPromo);
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

    public int getQtdProdutos() {return produtos.size();}

    public String getTipoMidia() {return tipoMidia;}

    public void setTipoMidia(String tipoMidia) {this.tipoMidia = tipoMidia;}

    private void setImagem(String imagem) { this.imagem = imagem; }

    public Drawable getImagem() { return Save.decode(imagem);}

    public void addProduto(Produto produto){ produtos.add(produto); }

    public ArrayList<Produto> getProdutos(){ return produtos; }


    public String getCorPromo() {
        return corPromo;
    }

    public void setCorPromo(String corPromo) {
        this.corPromo = corPromo;
    }

    public String getCorNormal() {
        return corNormal;
    }

    public void setCorNormal(String corNormal) {
        this.corNormal = corNormal;
    }
}
