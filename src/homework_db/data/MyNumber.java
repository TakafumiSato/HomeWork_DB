/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db.data;

/**
 *
 * @author 佐藤孝史
 * 
 * MyNumberデータクラス
 */
public class MyNumber {
    
    private int id = 0;
    private long myNumber = 0;
    
    public MyNumber(int id, long myNumber) {
        this.id = id;
        this.myNumber = myNumber;
    }
    
    public int getID() {
        return id;
    }
    
    public long getMyNumber() {
        return myNumber;
    }
}
