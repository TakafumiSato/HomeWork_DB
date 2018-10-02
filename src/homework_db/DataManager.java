/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 佐藤孝史
 */
public abstract class DataManager implements DBController {
    
    protected Connection connection = null;
    protected Logger logger = null;
    protected String tableName = null;
    
    public void openDB() {
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynumberdb?useUnicode=true&characterEncoding=utf8","TakafumiSato","1234567");
            
            System.out.println("データベース接続成功");
        } catch (SQLException se) {
            logger.log(Level.SEVERE, "データベース接続失敗", se);
            
            try {
                // 接続に失敗したらクローズ
                connection.close();
                connection = null;
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "データベースクローズ失敗", ex);
            }
        }
    }
    
    /*
    データベースをクローズ
    */
    public void closeDB() {
        
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection = null;
        }
    }
    
    /*
    mynumber_tableのテーブルデータを全取得
    戻り値:ArrayList<MyNumber>
    */
    public ArrayList<?> getAll() {
        
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<?> list = new ArrayList<>();
        
        // データベース オープン
        openDB();
        
        try {
            
            // データの取得
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + getTableName());

            list = readData(rs);
            
        } catch (SQLException ex) {
            
            System.out.println("SQLException(MyNumberTable:getAll):" + ex.getMessage());
            return new ArrayList<>();
            
        } finally {
            
            try {
                // Statement クローズ
                stmt.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Statement クローズ失敗", ex);
            }
            try {
                // ResultSet クローズ
                rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "ResultSet クローズ失敗", ex);
            }
            
            // データベース クローズ
            closeDB();
            stmt = null;
            rs = null;
            
        }
        
        return list;
    }
    
    protected abstract String getTableName();
    protected abstract ArrayList<?> readData(ResultSet rs);
}
