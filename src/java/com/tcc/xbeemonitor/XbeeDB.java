/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcc.xbeemonitor;

/**
 *
 * @author matheusgoes
 */
public class XbeeDB {
    private Integer id;
    private String local;
    private int estado;
    private int reservada;


    public String getIdString() {
        return id.toString();
    }

        /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the local
     */
    public String getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getStatusReserva() {
        return reservada;
    }

    public void setStatusReserva(int reservada) {
        this.reservada = reservada;
    }

    @Override
    public String toString() {
        return "XbeeDB{" + "id=" + id + ", local=" + local + ", estado=" + estado + ", reservada=" + reservada + '}';
    }

    public XbeeDB(Integer id, String local, int estado, int reservada) {
        this.id = id;
        this.local = local;
        this.estado = estado;
        this.reservada = reservada;
    }

    
       
   
}
