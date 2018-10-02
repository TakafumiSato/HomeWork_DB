/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db.manager;

import homework_db.DBController;
import homework_db.data.StaffMyNumber;
import homework_db.data.StaffMaster;
import homework_db.data.MyNumber;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * StaffMyNumberクラスに関する操作
 */
public class StaffMyNumberManager extends DataManager {
    
    // ソートのモードを指定
    public static enum SortMode {
        SELECT,
        BUCKET,
        BUBBLE,
        QUICK,
    }
    
    
    public StaffMyNumberManager() {
        
        tableName = "staffmynumber_table";
    }
    
    /*
    mynumber_tableのテーブル名を取得
    戻り値:String
    */
    @Override
    protected String getTableName() {
        
        return tableName;
    }
    
    /*
    ResultSetからデータを書き出し
    引数:ResultSet
    戻り値:ArrayList<StaffMyNumber>
    */
    @Override
    protected ArrayList<StaffMyNumber> readData(ResultSet rs) {
        
        ArrayList<StaffMyNumber> list = new ArrayList<StaffMyNumber>();
        
        try {
            // ResultSetのデータをdataListに書き出す
            while (rs.next()) {
                list.add(new StaffMyNumber(rs.getInt("id"), rs.getString("name"), rs.getString("gender"), rs.getInt("birth"), rs.getLong("myNumber")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffMyNumberManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    /*
    staffmynumber_table にデータを挿入
    */
    public void setAll(ArrayList<StaffMyNumber> list) {
        
        PreparedStatement ps = null;
        Connection connection = null;
        
        // データベースオープン
        DBController.openDB();
        connection = DBController.getConnection();
        
        try {
            // オートコミットをオフ
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(StaffMyNumberManager.class.getName()).log(Level.SEVERE, "オートコミット失敗", ex);
        }
        
        try {
            // SQL構文を登録 REPLACE
            ps = connection.prepareStatement(
                    "REPLACE INTO mynumberdb.staffmynumber_table (id, `name`, gender, birth, `myNumber`) VALUES(?,?,?,?,?)");
            
            // StaffMyNumberのデータをデータベースへ登録
            for (int i = 0; i < list.size(); i++) {
                
                ps.setInt(1, list.get(i).getID());
                ps.setString(2, list.get(i).getName());
                ps.setString(3, list.get(i).getGender());
                ps.setInt(4, list.get(i).getBirth());
                ps.setLong(5, list.get(i).getMyNumber());
                
                ps.executeUpdate();
            }
            // コミット
            connection.commit();
            
        } catch (SQLException ex) {
            try {
                // エラーの場合ロールバックし、登録を無効
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StaffMyNumberManager.class.getName()).log(Level.SEVERE, "ロールバック失敗", ex1);
            }
            
            out.println("SQLException(setAll):"+ex.getMessage());
            Logger.getLogger(StaffMyNumberManager.class.getName()).log(Level.INFO, "例外のスローを捕捉", ex);
            
        } finally {
            
            try {
                // PreparedStatement クローズ
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(StaffMyNumberManager.class.getName()).log(Level.SEVERE, "クローズ失敗", ex);
            }
            
            // データベースクローズ
            DBController.closeDB();
            connection = null;
            ps = null;
        }
    }
    
    /*
    StaffMasterとMyNumberをマージ
    引数:ArrayList<StaffMaster>,ArrayList<MyNumber>
    戻り値:ArrayList<StaffMyNumber>
    */
    public ArrayList<StaffMyNumber> mergeTable(ArrayList<StaffMaster> staffList, ArrayList<MyNumber> myNumberList) {
        
        // StaffMasterのIDと照らし合わせて整列させた個人番号を配列で確保
        long[] numbers = new long[staffList.size()];
        for (int i = 0; i < myNumberList.size(); i++) {
            
            numbers[searchID(staffList, myNumberList.get(i).getID())] = myNumberList.get(i).getMyNumber();
        }
        
        // StaffMasterの従業員番号から年齢までと整列させたnumbers(MyNumber)を
        // StaffMyNumberへと落し込み
        ArrayList<StaffMyNumber> resultList = new ArrayList<StaffMyNumber>();
        for (int i = 0; i < staffList.size(); i++) {
            
            resultList.add(new StaffMyNumber(staffList.get(i).getID(),
                                                    staffList.get(i).getName(),
                                                    staffList.get(i).getGender(),
                                                    staffList.get(i).getBirth(),
                                                    numbers[i]));
        }
        
        numbers = null;
        
        return resultList;
    }
    
    /*
    StaffMasterをID検索
    引数:ArrayList<StaffMaster>,int
    */
    private int searchID(ArrayList<StaffMaster> list, int id) {
        
        int index = 99999;

        // 該当するIDを抽出
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getID() == id) {
                index = i;
                break;
            }
        }

        return index;
    }
    
    public void sortAge(ArrayList<StaffMyNumber> list, SortMode sortMode) {
        
        // ソートの種類を選択
        switch (sortMode) {
            // 選択ソート
            case SELECT:
                selectSortAge(list);
                break;
            // バケットソート
            case BUCKET:
                bucketSortAge(list);
                break;
            // バブルソート
            case BUBBLE:
                bubbleSortAge(list);
                break;
            // バブルソート
            case QUICK:
                quickSortAge(list, 0, list.size()-1);
                break;
            default:
                System.out.println("そんなモードはありません");
                break;
        }
    }
    
    public static void selectSortAge(ArrayList<StaffMyNumber> list) {
        
        System.out.println("*選択ソート*");

        for(int i = 0; i < list.size(); i++ ){
            int index = i;
            for(int j = i + 1; j < list.size(); j++){
                if(list.get(j).getAge() < list.get(index).getAge()){
                    index = j;
                }
            }
            if(i != index){
                StaffMyNumber tmp = list.get(index);
                list.set(index, list.get(i));
                list.set(i,tmp);
            }
        }
    }
    
    public static void bucketSortAge(ArrayList<StaffMyNumber> list) {
        
        System.out.println("*バケットソート*");
        
        ArrayList<ArrayList<StaffMyNumber>> bucket = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            bucket.add(new ArrayList<>());
        }
        
        for (int i = 0; i < list.size(); i++) {
            bucket.get(list.get(i).getAge()).add(list.get(i));
        }
        
        int j = 0;
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i) != null) {
                while (bucket.get(i).size() > 0) {
                    StaffMyNumber tmp = (StaffMyNumber)bucket.get(i).get(0);
                    bucket.get(i).remove(0);
                    list.set(j, tmp);
                    j++;
                }
            }
        }
        
        bucket = null;
    }
    
    public static void bubbleSortAge(ArrayList<StaffMyNumber> list) {
        
        System.out.println("*バブルソート*");
        
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size()-1; j > i; j--) {
                System.out.println(list.get(j).getAge() + "," + list.get(j-1).getAge());
                if (list.get(j).getAge() < list.get(j-1).getAge()) {
                    StaffMyNumber tmp = list.get(j);
                    list.set(j, list.get(j-1));
                    list.set(j-1,tmp);
                }
            }
            System.out.println(list.get(i).getAge());
        }
    }
    
    public static void quickSortAge(ArrayList<StaffMyNumber> list, int left, int right) {
        
        System.out.println("*クイックソート*");
        
        if (left >= right) {
            return;
        }
        
        // ピボット値を決める
        int pivot = setMedian(list.get(left).getAge(), list.get(left + (right - left) / 2).getAge(), list.get(right).getAge());
        
        int l = left;
        int r = right;
        
        while (true) {
            // ピボットより小さいデータのインデックスを見つける
            while (Integer.valueOf(list.get(l).getAge()).compareTo(pivot) < 0) {
                l++;
            }
            // ピボットより大きいデータのインデックスを見つける
            while (Integer.valueOf(list.get(r).getAge()).compareTo(pivot) > 0) {
                r--;
            }
            // 見つかったインデックスの大小が逆転していれば、終了
            if (l >= r) {
                break;
            }
            
            StaffMyNumber tmp = list.get(l);
            list.set(l, list.get(r));
            list.set(r,tmp);
            
            l++;
            r--;
        }
        
        quickSortAge(list, left, l-1);
        quickSortAge(list, r+1, right);
    }
    
    public static int setMedian(Integer x, Integer y, Integer z) {
        
        if (x.compareTo(y) < 0) {
            return x.compareTo(z) < 0 ? (y.compareTo(z) < 0 ? y : z) : x;
        } else {
            return y.compareTo(z) < 0 ? (x.compareTo(z) < 0 ? x : z) : y;
        }
    }
}
