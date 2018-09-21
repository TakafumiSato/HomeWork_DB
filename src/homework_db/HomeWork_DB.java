/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 佐藤孝史
 */
public class HomeWork_DB {

    public static final int SORT_MODE_SELECT = 1;
    public static final int SORT_MODE_BUCKET = 2;
    
    static DBController dbController = new DBController();
    
    static ArrayList<StaffMaster> staffList = new ArrayList<StaffMaster>();
    static ArrayList<MyNumber> myNumberList = new ArrayList<MyNumber>();
    static ArrayList<StaffMyNumber> staffMyNumberList = new ArrayList<StaffMyNumber>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        loadFile();
        
        mergeData();
        
        sortAge(SORT_MODE_BUCKET);
        
        dbController.setStaffMyNumberTable(staffMyNumberList);
    }
    
    public static void loadFile() {
        
        String[] list = dbController.getDataTable("select * from staffmaster_table");

        
        for (int i = 0; i < list.length; i+=StaffMaster.MAX_COLUMN) {
            staffList.add(new StaffMaster(Integer.parseInt(list[i]), list[i+1], list[i+2], Integer.parseInt(list[i+3])));
        }
        
        list = dbController.getDataTable("select * from mynumber_table");

        
        for (int i = 0; i < list.length; i+=MyNumber.MAX_COLUMN) {
            myNumberList.add(new MyNumber(Integer.parseInt(list[i]), Long.parseLong(list[i+1])));
        }
    }
    
    static void mergeData() {
        
        // StaffMasterのIDと照らし合わせて整列させた個人番号を配列で確保
        long[] numbers = new long[staffList.size()];
        for (int i = 0; i < myNumberList.size(); i++) {
            
            numbers[searchID(myNumberList.get(i).getID())] = myNumberList.get(i).getMyNumber();
        }
        
        // StaffMasterの従業員番号から年齢までと整列させたnumbers(MyNumber)を
        // StaffMyNumberへと落し込み
        for (int i = 0; i < staffList.size(); i++) {
            staffMyNumberList.add(new StaffMyNumber(staffList.get(i).getID(),
                                                    staffList.get(i).getName(),
                                                    staffList.get(i).getGender(),
                                                    staffList.get(i).getBirth(),
                                                    numbers[i]));
        }
    }
    
    static int searchID(int id) {
        
        int index = 99999;

        // 該当するIDを抽出
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getID() == id) {
                index = i;
                break;
            }
        }

        return index;
    }
    
    public static void sortAge(int sortMode) {
        
        // ソートの種類を選択
        switch (sortMode) {
            // 選択ソート
            case SORT_MODE_SELECT:
                selectSortAge();
                break;
            // バケットソート
            case SORT_MODE_BUCKET:
                bucketSortAge();
                break;
            default:
                System.out.println("そんなモードはありません");
                break;
        }
    }
    
    public static void selectSortAge() {
        
        System.out.println("*選択ソート*");
        
        int[] array = new int[staffMyNumberList.size()];
        
        for (int i = 0; i < staffMyNumberList.size(); i++) {
            array[i] = staffMyNumberList.get(i).getAge();
        }

        for(int i = 0; i < staffMyNumberList.size(); i++ ){
            int index = i;
            for(int j = i + 1; j < staffMyNumberList.size(); j++){
                if(staffMyNumberList.get(j).getAge() < staffMyNumberList.get(index).getAge()){
                    index = j;
                }
            }
            if(i != index){
                StaffMyNumber tmp = staffMyNumberList.get(index);
                staffMyNumberList.set(index, staffMyNumberList.get(i));
                staffMyNumberList.set(i,tmp);
            }
        }
    }
    
    public static void bucketSortAge() {
        
        System.out.println("*バケットソート*");
        
        ArrayList<ArrayList<StaffMyNumber>> bucket = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            bucket.add(new ArrayList<>());
        }
        
        for (int i = 0; i < staffMyNumberList.size(); i++) {
            bucket.get(staffMyNumberList.get(i).getAge()).add(staffMyNumberList.get(i));
        }
        
        int j = 0;
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i) != null) {
                while (bucket.get(i).size() > 0) {
                    StaffMyNumber tmp = (StaffMyNumber)bucket.get(i).get(0);
                    bucket.get(i).remove(0);
                    staffMyNumberList.set(j, tmp);
                    j++;
                }
            }
        }
    }
    
}
