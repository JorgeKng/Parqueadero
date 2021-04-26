/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istloja.modelo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;




public class Vehiculo {
    private int Id;
    private String Placa = "";
    private String Propietario="";
    private String tipo_Vehiculo="";
    private String hora_Entrada="";
    private String hora_Salida="" ;
    private double valor_Pagar = 0;
    private String Estado="";
    
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public String getPropietario() {
        return Propietario;
    }

    public void setPropietario(String Propietario) {
        this.Propietario = Propietario;
    }

    public String getTipo_Vehiculo() {
        return tipo_Vehiculo;
    }

    public void setTipo_Vehiculo(String tipo_Vehiculo) {
        this.tipo_Vehiculo = tipo_Vehiculo;
    }

    public String getHora_Entrada() {
        return hora_Entrada;
    }

    public void setHora_Entrada(String hora_Entrada) {
        this.hora_Entrada = hora_Entrada;
    }

    public String getHora_Salida() {
        return hora_Salida;
    }

    public void setHora_Salida(String hora_Salida) {
        this.hora_Salida = hora_Salida;
    }

    public double getValor_Pagar() {
        return valor_Pagar;
    }

    public void setValor_Pagar(double valor_Pagar) {
        this.valor_Pagar = valor_Pagar;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    

    @Override
    public String toString() {
        return "Vehiculo{" + "Id=" + Id + ", Placa=" + Placa + ", Propietario=" + Propietario + ", tipo_Vehiculo=" + tipo_Vehiculo + ", hora_Entrada=" + hora_Entrada + ", hora_Salida=" + hora_Salida + ", valor_Pagar=" + valor_Pagar + ", Estado=" + Estado + '}';
    }

    }
