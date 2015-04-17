/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcc.xbeemonitor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author matheusgoes
 */
public class MensagemDAO {
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public Connection getConnection() {
        Connection conn = ConnectionFactory.getInstance().getConnection();
        return conn;
    }

    public boolean insert(Mensagem mensagem) {
        conn = getConnection();

        String sql = "Insert INTO Mensagem (Mensagem, Remetente) value(?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mensagem.getMensagem());
            pstmt.setInt(2, mensagem.getRemetente());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public Mensagem getByRemetente(int id) {
        try {
            String sql = "Select * from Mensagem where Remetente = ?";
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String msg = rs.getString("Mensagem");
                Mensagem x = new Mensagem(msg, id);
                return x;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
