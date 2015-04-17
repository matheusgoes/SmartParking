/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goes.smartparking;

import java.sql.Date;

/**
 *
 * @author matheusgoes
 */
public class Mensagem {
    Date data;
    String Mensagem;
    int remetente;

    public Mensagem(String Mensagem, int remetente) {
        this.Mensagem = Mensagem;
        this.remetente = remetente;
    }

    public Mensagem(Date data, String Mensagem) {
        this.data = data;
        this.Mensagem = Mensagem;
    }
    
    

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String Mensagem) {
        this.Mensagem = Mensagem;
    }

    public int getRemetente() {
        return remetente;
    }

    public void setRemetente(int remetente) {
        this.remetente = remetente;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "data=" + data + ", Mensagem=" + Mensagem + ", remetente=" + remetente + '}';
    }
    
    
}
