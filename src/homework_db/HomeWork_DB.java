/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db;

import homework_db.manager.StaffMasterManager;
import homework_db.manager.MyNumberManager;
import homework_db.manager.StaffMyNumberManager;
import homework_db.data.StaffMyNumber;
import homework_db.data.StaffMaster;
import homework_db.data.MyNumber;
import java.util.ArrayList;

/**
 *
 * @author 佐藤孝史
 */
public class HomeWork_DB {
    
    static ArrayList<StaffMaster> staffList = new ArrayList<StaffMaster>();
    static ArrayList<MyNumber> myNumberList = new ArrayList<MyNumber>();
    static ArrayList<StaffMyNumber> staffMyNumberList = new ArrayList<StaffMyNumber>();
    static StaffMasterManager staffManager = new StaffMasterManager();
    static MyNumberManager myNumberManager = new MyNumberManager();
    static StaffMyNumberManager staffMyNumberManager = new StaffMyNumberManager();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // テーブルデータを取得
        staffList = (ArrayList<StaffMaster>) staffManager.getAll();
        myNumberList = (ArrayList<MyNumber>) myNumberManager.getAll();
        
        // テーブルデータをマージ
        staffMyNumberList = staffMyNumberManager.mergeTable(staffList, myNumberList);
        
        // マージしたデータをソート
        staffMyNumberManager.sortAge(staffMyNumberList, StaffMyNumberManager.SortMode.QUICK);
        
        // マージ、ソートしたデータをテーブルにセット
        staffMyNumberManager.setAll(staffMyNumberList);
    }
}
