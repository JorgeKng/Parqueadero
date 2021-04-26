/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istloja.modelTable;

import com.istloja.modelo.Vehiculo;
import com.istloja.vistas.Listar;
import java.util.List;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Jorge Kng
 */
public class ModelTableListar extends AbstractTableModel {

    private String[] m_colNames = {"ID", "PLACA", "PROPIETARIO", "TIPO","HORA ENTRADA","HORA SALIDA","V. PAGAR","ESTADO"};
    private List<Vehiculo> Vehiculosbusq;
    private Listar listar;

    public ModelTableListar(List<Vehiculo> Vehiculosbusq, Listar listar) {
        this.Vehiculosbusq = Vehiculosbusq;
        this.listar = listar;
    }

    //Determina el número de filas que tengo en mi tabla.
    @Override
    public int getRowCount() {
        return Vehiculosbusq.size();
    }

    //Determina el número de columnas que tengo en mi tabla.
    @Override
    public int getColumnCount() {
        return m_colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vehiculo b = Vehiculosbusq.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return b.getId();
            case 1:
                return b.getPlaca();
            case 2:
                return b.getPropietario();
            case 3:
                return b.getTipo_Vehiculo();
            case 4:
                return b.getHora_Entrada();
            case 5:
                return b.getHora_Salida();
            case 6:
                return b.getValor_Pagar();
            case 7:
                return b.getEstado();
            
        }
        return new String();
    }

    @Override
    public String getColumnName(int column) {
        return m_colNames[column]; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        listar.clickVehiculo(Vehiculosbusq.get(rowIndex));
        return super.isCellEditable(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Vehiculo> getVehiculosbusq() {
        return Vehiculosbusq;
    }

    public void setVehiculosbusq(List<Vehiculo> Vehiculosbusq) {
        this.Vehiculosbusq = Vehiculosbusq;
    }

    
    

    
}
