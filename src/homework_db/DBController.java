/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author 佐藤孝史
 */
public class DBController {
    
    private Connection con = null;
    
    public DBController() {
        
        connectDataBase();
    }
    
    private void connectDataBase() {
        
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynumberdb?useUnicode=true&characterEncoding=utf8","TakafumiSato","1234567");
            
            System.out.println("MySQLに接続しました。");
        } catch (SQLException se) {
            System.out.println("MySQLに接続できませんでした。");
            System.out.println(se);
        }
    }
    
    public String[] getDataTable(String tableName) {
        
        ResultSet rs = null;
        String[] dataList = null;
        
        try {
            // データの取得
            Statement stmt = con.createStatement();
            String sql = tableName;
            rs = stmt.executeQuery(sql);
            // テーブルの行と列を取得
            rs.last();
            int row = rs.getRow();
            rs.beforeFirst();
            int column = rs.getMetaData().getColumnCount();
            // dataListを初期化
            dataList = new String[row*column];
            
            // ResultSetのデータをdataListに書き出す
            int index = 0;
            for (int i = 0; i < row; i++) {
                rs.next();
                for (int j = 1; j <= column; j++) {
                    dataList[index] = rs.getString(j);
                    System.out.println(dataList[index]);
                    index++;
                }
            }
            
            // クローズ
            stmt.close();
        } catch (SQLException ex) {
            out.println("SQLException:" + ex.getMessage());
        }
        
        return dataList;
    }
    
    public void setStaffMyNumberTable(ArrayList<StaffMyNumber> dataList) {
        
        try {
            PreparedStatement ps = null;
            // SQL構文を登録
            ps = con.prepareStatement(
                    "INSERT INTO mynumberdb.staffmynumber_table (id, `name`, gender, birth, `myNumber`) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE name=VALUES(name),gender=VALUES(gender),birth=VALUES(birth),myNumber=VALUES(myNumber)");
    
            // StaffMyNumberのデータをデータベースへ登録
            for (int i = 0; i < dataList.size(); i++) {
                ps.setInt(1, dataList.get(i).getID());
                ps.setString(2, dataList.get(i).getName());
                ps.setString(3, dataList.get(i).getGender());
                ps.setInt(4, dataList.get(i).getBirth());
                ps.setLong(5, dataList.get(i).getMyNumber());
                ps.executeUpdate();
            }
            
        } catch (SQLException ex) {
            out.println("SQLException:" + ex.getMessage());
        }
    }
}
