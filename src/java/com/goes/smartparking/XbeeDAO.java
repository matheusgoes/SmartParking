/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goes.smartparking;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alba
 */
public class XbeeDAO {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public Connection getConnection() {
        Connection conn = ConnectionFactory.getInstance().getConnection();
        return conn;
    }

    public boolean insert(XbeeDB xbee) {
        conn = getConnection();
        String sql = "Insert INTO Xbee (idXbee, Local, Estado) value(?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, xbee.getId());
            pstmt.setString(2, xbee.getLocal());
            pstmt.setInt(1, xbee.getEstado());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void updateLocal(XbeeDB xbee) {
        conn = getConnection();
        String sql = "Update Xbee set Local = ? where idXbee = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, xbee.getLocal());
            pstmt.setInt(2, xbee.getId());
            pstmt.executeUpdate();
            System.out.println("Dado atualizado com sucesso");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateReserva(int value,int id) {
        conn = getConnection();
        String sql = "Update Xbee set Reservada = ? where idXbee = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, value);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Dado atualizado com sucesso");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateEstado(XbeeDB xbee) {
        conn = getConnection();
        String sql = "Update Xbee set Estado = ? where idXbee = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, xbee.getEstado());
            pstmt.setInt(2, xbee.getId());
            pstmt.executeUpdate();
            System.out.println("Dado atualizado com sucesso");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(XbeeDB xbee) {
        conn = getConnection();
        String sql = "Delete from Xbee where idXbee = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, xbee.getId());
            pstmt.executeUpdate();
            System.out.println("Dado excluído com sucesso");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public XbeeDB getById(int id) {
        try {
            String sql = "Select * from xbee where idXbee = ?";
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idxbee = rs.getInt("idxbee");
                String Local = rs.getString("Local");
                int Estado = rs.getInt("Estado");
                int Reservada = rs.getInt("Reservada");
                XbeeDB x = new XbeeDB(idxbee, Local, Estado, Reservada);
                return x;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<XbeeDB> getAll() {
        try {
            ArrayList<XbeeDB> retorno = new ArrayList<XbeeDB>();
            String sql = "Select * from xbee";
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idxbee = rs.getInt("idxbee");
                String Local = rs.getString("Local");
                int Estado = rs.getInt("Estado");
                int Reservada = rs.getInt("Reservada");
                XbeeDB x = new XbeeDB(idxbee, Local, Estado, Reservada);
                retorno.add(x);
            }
            return retorno;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
