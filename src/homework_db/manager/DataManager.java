/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db.manager;

import homework_db.DBController;
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
public abstract class DataManager {

    protected String tableName = null;
    
    
    
    /*
    mynumber_tableのテーブルデータを全取得
    戻り値:ArrayList<?>
    */
    public ArrayList<?> getAll() {
        
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<?> list = new ArrayList<>();
        
        // データベース オープン
        DBController dbController = new DBController();
        connection = dbController.openDB();
        
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
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, "Statement クローズ失敗", ex);
            }
            try {
                // ResultSet クローズ
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, "ResultSet クローズ失敗", ex);
            }
            
            // データベース クローズ
            dbController.closeDB();
            connection = null;
            stmt = null;
            rs = null;
            
        }
        
        return list;
    }
    
    protected abstract String getTableName();
    protected abstract ArrayList<?> readData(ResultSet rs);
}
