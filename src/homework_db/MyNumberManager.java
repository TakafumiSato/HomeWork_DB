/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db;

import homework_db.data.MyNumber;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 佐藤孝史
 * 
 * マイナンバークラスに関する操作
 */
public class MyNumberManager extends DataManager {
    
    private static final String tableName = "mynumber_table";
    
    public MyNumberManager() {
        
        logger = Logger.getLogger(MyNumberManager.class.getName());
    }
    
    /*
    mynumber_tableのテーブル名を取得
    戻り値:String
    */
    @Override
    protected String getTableName() {
        
        return tableName;
    }
    
    @Override
    protected ArrayList<MyNumber> readData(ResultSet rs) {
        
        ArrayList<MyNumber> list = new ArrayList<MyNumber>();
        
        try {
            // ResultSetのデータをdataListに書き出す
            while (rs.next()) {
                list.add(new MyNumber(rs.getInt("id"),rs.getLong("myNumber")));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
}
