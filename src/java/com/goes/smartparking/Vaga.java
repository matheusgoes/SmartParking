/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goes.smartparking;

/**
 *
 * @author matheusgoes
 */
public class Vaga {
    private String vaga;
    private String idXbee;
    private char estado;

    public Vaga(String vaga, String idXbee, char estado) {
        this.vaga = vaga;
        this.idXbee = idXbee;
        this.estado = estado;
    }

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

    public String getIdXbee() {
        return idXbee;
    }

    public void setIdXbee(String idXbee) {
        this.idXbee = idXbee;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    
}
