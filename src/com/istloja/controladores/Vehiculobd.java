/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.istloja.controladores;

import com.istloja.conexionbd.Conexion;
import com.istloja.modelo.Vehiculo;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge Kng
 */
public class Vehiculobd {
    
    
    public boolean IngresarVehiculo(Vehiculo vehiculo) {
        
        
        boolean registrar = false;
        //Interfaz de acceso a la base de datos
        Statement stm = null;
        //Conexion con la base de datos.
        Connection con = null;
        
        String sql = "INSERT INTO `parqueadero`.`vehiculo` (`placa`, `propietario`, `tipo_vehiculo`, `hora_entrada`, `estado`) VALUES ( '"+vehiculo.getPlaca()+"', '"+vehiculo.getPropietario()+"', '"+vehiculo.getTipo_Vehiculo()+"', '"+vehiculo.getHora_Entrada()+"', 'Disponible');";
                try {
            //Es una instancia de la conexion previamente creada.
            
            Conexion conexion = new Conexion();
            con = conexion.conectarBaseDatos();
            stm = con.createStatement();
            stm.execute(sql);
            
            registrar = true;
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return registrar;
    }
    public boolean ActualizarVehiculo(Vehiculo vehiculo) {
        
        
        boolean registrar = false;
        //Interfaz de acceso a la base de datos
        Statement stm = null;
        //Conexion con la base de datos.
        Connection con = null;
        
        String sql = "update parqueadero.vehiculo SET hora_entrada ='"+vehiculo.getHora_Entrada()+"',estado='Disponible',tipo_vehiculo='"+vehiculo.getTipo_Vehiculo()+"', propietario = '"+vehiculo.getPropietario()+"' where placa='"+vehiculo.getPlaca()+"'";
                try {
            //Es una instancia de la conexion previamente creada.
            
            Conexion conexion = new Conexion();
            con = conexion.conectarBaseDatos();
            stm = con.createStatement();
            stm.execute(sql);
            
            registrar = true;
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return registrar;
    }
    public boolean EliminarVehiculo(Vehiculo vehiculo) {
        boolean registrar = false;
        //Interfaz de acceso a la base de datos
        Statement stm = null;
        //Conexion con la base de datos.
        Connection con = null;
        
        String sql = "DELETE FROM `parqueadero`.`vehiculo` WHERE (`placa` = '"+vehiculo.getPlaca()+"');";
                try {
            //Es una instancia de la conexion previamente creada.
            
            Conexion conexion = new Conexion();
            con = conexion.conectarBaseDatos();
            stm = con.createStatement();
            stm.execute(sql);
            
            registrar = true;
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return registrar;
    }
    public boolean  ObeterVehiculoPlaca(Vehiculo vehiculo) {
        boolean registrar = false;
        //Interfaz de acceso a la base de datos
        Statement stm = null;
        //Conexion con la base de datos.
        Connection co = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM parqueadero.vehiculo where placa = '"+vehiculo.getPlaca()+"' ;";
        
             List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
        try {
            co = new Conexion().conectarBaseDatos();
            stm = co.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
               
                Vehiculo v = new Vehiculo();
                v.setId(rs.getInt(1));
                v.setPlaca(rs.getString(2));
                v.setPropietario(rs.getString(3));
                v.setTipo_Vehiculo(rs.getString(4));
                System.out.println(rs.getString(5));
                v.setHora_Entrada(rs.getString(5).substring(10).substring(0, 6));
                if(rs.getString(6)!=null){
                    v.setHora_Salida(rs.getString(6).substring(10).substring(0, 6));
                }else{
                    v.setHora_Salida("Aun no ha salido");
                }                                                 
                v.setValor_Pagar(dosDecimales(rs.getDouble(7)));
                v.setEstado(rs.getString(8));
                
                listaVehiculos.add(v);
                
            }
            stm.close();
            rs.close();
            co.close();
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
            
        }
        if(!listaVehiculos.isEmpty()){
            System.out.println(listaVehiculos);
            registrar = true;
        }else{
            registrar = false;
            System.out.println(registrar);
        }
        return registrar;
    }
    
    
    public boolean RetirarVehiculo(Vehiculo v){
        String date1= "";
        double Total = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        date1 = dateFormat.format(date);
        Connection co = null;
        Statement stm = null;
        //Sentencia de JDBC para obtener valores de la base de datos.
        ResultSet rs = null;
        String sql = "select hora_entrada,tipo_vehiculo from parqueadero.vehiculo where placa = '"+v.getPlaca()+"';";
            try {
            co = new Conexion().conectarBaseDatos();
            stm = co.createStatement();
            rs = stm.executeQuery(sql);
            //Obtengo el primer valor que me envia como resultado de la consulta
            rs.first();
            //Ahora casteo a un String para darle formato
            String HoraEntrada = rs.getString(1);
            //Le doy Formato
            Date horaSalida = dateFormat.parse(HoraEntrada);  
            //Calculo el valor a cobrar por el servicio
            int minutosacobrar = (int) ((date.getTime()-horaSalida.getTime())/60000);
            //Si es automovil la tarifa es mas alta
            if(rs.getString(2).equals("Automovil")){
                Total = minutosacobrar*0.01666667;
            }
            //Mientras que para moto reduce
            if(rs.getString(2).equals("Motocicleta")){
                Total = minutosacobrar*0.0125;
            }
            //Imprimo los valores que voy a mostrar
            JOptionPane.showMessageDialog(null, "Se retiró. Valor a pagar por: "+rs.getString(2)+" = "+Total+" Hora Salida: " +date1);
            //Actualizo la base de Datos cambiando el estado A que ya se retiro 
            String actualizar = "update parqueadero.vehiculo SET hora_salida='"+date1+"',estado='No Disponible',valora_pagar='"+Total+"' where placa='"+v.getPlaca()+"' and estado = 'Disponible';";
            stm.executeUpdate(actualizar);
            //Actualizo y listo
            stm.close();
            rs.close();
            co.close();
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(Vehiculobd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    public double dosDecimales(double entrada){
        return Math.round(entrada*100.0)/100.0;
    }
    
    public List<Vehiculo> obtenerVehiculos() {
        Connection co = null;
        Statement stm = null;
        //Sentencia de JDBC para obtener valores de la base de datos.
        ResultSet rs = null;
        String sql = "select * from parqueadero.vehiculo;";
        //Creo la lista
        List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
        try {
            co = new Conexion().conectarBaseDatos();
            stm = co.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
               //Instancio un objeto el cual recibe los valores de la tabla
                Vehiculo v = new Vehiculo();
                v.setId(rs.getInt(1));
                v.setPlaca(rs.getString(2));
                v.setPropietario(rs.getString(3));
                v.setTipo_Vehiculo(rs.getString(4));
                
                v.setHora_Entrada((rs.getString(5).substring(10).substring(0, 6))+05);
                if(rs.getString(6)!=null){
                    v.setHora_Salida((rs.getString(6).substring(10).substring(0, 6))+5);
                }else{
                    v.setHora_Salida("Aun no ha salido");
                }
                v.setValor_Pagar(dosDecimales(rs.getDouble(7)));
                v.setEstado(rs.getString(8));
                //Una ves seteados agrego el objeto a mi lista y asi hasta que termine
                listaVehiculos.add(v);
                }
             stm.close();
            rs.close();
            co.close();
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
// Al final retorno la lista
        return listaVehiculos;
    }
    public List<Vehiculo>  BuscarVehiculo(Vehiculo vehiculo){
        
        
        Connection co = null;
        Statement stm = null;
        //Sentencia de JDBC para obtener valores de la base de datos.
        ResultSet rs = null;
        System.out.println(vehiculo);
        String sql = "select * from parqueadero.vehiculo where placa like '%"+vehiculo.getPlaca()+"%' and propietario like '%"+vehiculo.getPropietario()+"%'and hora_entrada like '%"+vehiculo.getHora_Entrada()+"%' and tipo_vehiculo like '%"+vehiculo.getTipo_Vehiculo()+"%' and estado like'"+vehiculo.getEstado()+"';";
         List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
         
        try {
            co = new Conexion().conectarBaseDatos();
            stm = co.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                v.setId(rs.getInt(1));
                v.setPlaca(rs.getString(2));
                v.setPropietario(rs.getString(3));
                v.setTipo_Vehiculo(rs.getString(4));
                
                v.setHora_Entrada(rs.getString(5).substring(10).substring(0, 6));
                if(rs.getString(6)!=null){
                    v.setHora_Salida(rs.getString(6).substring(10).substring(0, 6));
                }else{
                    v.setHora_Salida("Aun no ha salido");
                }
                    
               
                
                v.setValor_Pagar(dosDecimales(rs.getDouble(7)));
                v.setEstado(rs.getString(8));
                
                listaVehiculos.add(v);
                
            }
            
            stm.close();
            rs.close();
            co.close();
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
        return listaVehiculos;
}
    public double Total(Vehiculo vehiculo){
        double IngresosTotales = 0;
        boolean registrar = false;
        //Interfaz de acceso a la base de datos
        Statement stm = null;
        //Conexion con la base de datos.
        Connection con = null;
        
        String sql = "select sum(valora_pagar) from parqueadero.vehiculo where placa like '%"+vehiculo.getPlaca()+"%' and propietario like '%"+vehiculo.getPropietario()+"%' and hora_salida like '%"+vehiculo.getHora_Salida()+"%' and tipo_vehiculo like '%"+vehiculo.getTipo_Vehiculo()+"%' and estado like'%"+vehiculo.getEstado()+"%' ;";
        try {
            //Es una instancia de la conexion previamente creada.
            
            Conexion conexion = new Conexion();
            con = conexion.conectarBaseDatos();
            stm = con.createStatement();
            stm.execute(sql);
            
            ResultSet rs = stm.executeQuery(sql);
            rs.first();
            DecimalFormat df = new DecimalFormat("#.00");
            System.out.println(rs.getDouble(1));
            IngresosTotales = Double.parseDouble(rs.getString(1));
            df.format(IngresosTotales);
            JOptionPane.showMessageDialog(null, "El ingreso total del dia seleccionado es de : $ " + df.format(IngresosTotales) + " Dólares ");
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return IngresosTotales;
    }
   } 