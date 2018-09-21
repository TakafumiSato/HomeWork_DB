/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author 佐藤孝史
 */
public class StaffMyNumber {
    
    private int id;
    private String name;
    private String gender;
    private int birth;
    private int age;
    private long myNumber;
    
    public StaffMyNumber(int id, String name, String gender, int birth, long myNumber) {
        
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.myNumber = myNumber;
        
        setAge();
    }
    
    public int getID() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public int getBirth() {
        return birth;
    }
    
    public long getMyNumber() {
        return myNumber;
    }
    
    private void setAge() {
        Date now = new Date();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        age = (Integer.parseInt(sdf.format(now)) - birth) / 10000;
    }
    
    public int getAge() {
        return age;
    }
}
