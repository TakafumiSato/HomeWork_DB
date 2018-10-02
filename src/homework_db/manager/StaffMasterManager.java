/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db.manager;

import homework_db.data.StaffMaster;
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
 * 
 * 従業員マスタークラスに関する操作
 */
public class StaffMasterManager extends DataManager {
    
    
    public StaffMasterManager() {
        
        tableName = "staffmaster_table";
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
    protected ArrayList<StaffMaster> readData(ResultSet rs) {
        
        ArrayList<StaffMaster> list = new ArrayList<StaffMaster>();
        
        try {
            // ResultSetのデータをdataListに書き出す
            while (rs.next()) {
                list.add(new StaffMaster(rs.getInt("id"), rs.getString("name"), rs.getString("gender"), rs.getInt("birth")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyNumberManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
}
