/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anbar.dao;

import anbar.configuration.SQLiteConnection;
import anbar.model.Hesabat;
import anbar.model.Mehsul;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mardan Safarov
 */
public class MainDao {

    Connection conn = SQLiteConnection.connect();

    public List<Mehsul> list(String tableName) throws SQLException {
        List<Mehsul> mehsullar = new ArrayList();
        String sql = "select id, name, number from " + tableName;
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Mehsul mehsul = new Mehsul();
            mehsul.setId(rs.getInt("id"));
            mehsul.setName(rs.getString("name"));
            mehsul.setNumber(rs.getInt("number"));
            mehsullar.add(mehsul);
        }
        return mehsullar;
    }

    public void save(String name, int number, String tableName) throws SQLException {
        String sql = "insert into " + tableName + "(name, number) values (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        statement.setInt(2, number);
        statement.execute();
    }

    public void update(int id, String name, int number, String tableName) throws SQLException {
        String sql = "update " + tableName + " set name = ?, number = ? where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        statement.setInt(2, number);
        statement.setInt(3, id);
        statement.execute();
    }
        public void updateCar(String name, int number) throws SQLException {
        String sql = "update mashin set number = number + ? where name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);        
        statement.setInt(1, number);
        statement.setString(2, name);
        statement.execute();
    }

    public void delete(int id, String tableName) throws SQLException {
        String sql = "delete from " + tableName + " where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        statement.execute();
    }

    public List<Hesabat> listHesab(String reason) throws SQLException {
        List<Hesabat> hesablar = new ArrayList();
        String sql = "select date, product, number, action, reason from hesabat where reason = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, reason);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Hesabat hesabat = new Hesabat();
            hesabat.setDate(rs.getString("date"));
            hesabat.setName(rs.getString("product"));
            hesabat.setNumber(rs.getInt("number"));
            hesabat.setAction(rs.getString("action"));
            hesabat.setReason(rs.getString("reason"));
            hesablar.add(hesabat);
        }
        return hesablar;
    }

    public void saveHesab(String name, int number, String action, String reason) throws SQLException {
        String sql = "insert into hesabat (date, product, number, action, reason) values (?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDate = formatter.format(new Date());
        statement.setString(1, currentDate);
        statement.setString(2, name);
        statement.setInt(3, number);
        statement.setString(4, action);
        statement.setString(5, reason);
        statement.execute();
    }
    int check = 0;

    public int checkDb(String name) throws SQLException {
        String sql = "select * from mashin where name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            check++;
        }
        return check;
    }

    public String getNum(int id) throws SQLException {
        String sql = "select number from mashin where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        return sql;
    }
}
