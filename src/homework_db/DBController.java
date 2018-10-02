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
public interface DBController {
    
    /*
    データベースをオープン
    */
    public void openDB();
    
    /*
    データベースをクローズ
    */
    public void closeDB();
}
