/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 佐藤孝史
 * 
 * データベースへのアクセスを管理
 */
public class DBController {
    
    private static Connection connection = null;

    public static Connection getConnection() {
        return connection;
    }
    
    public static void openDB() {
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynumberdb?useUnicode=true&characterEncoding=utf8","TakafumiSato","1234567");
            
            System.out.println("データベース接続成功");
        } catch (SQLException se) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, "データベース接続失敗", se);
            
            try {
                // 接続に失敗したらクローズ
                connection.close();
                connection = null;
            } catch (SQLException ex) {
                Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, "データベースクローズ失敗", ex);
            }
        }
    }
    
    /*
    データベースをクローズ
    */
    public static void closeDB() {
        
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection = null;
        }
    }
}
