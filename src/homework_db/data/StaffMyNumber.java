/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db.data;

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
    
    public void setData(StaffMyNumber data) {
        
        this.id = data.getID();
        this.name = data.getName();
        this.gender = data.getGender();
        this.birth = data.getBirth();
        this.myNumber = data.getMyNumber();
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
    
    public void setName(String name) {
        this.name = name;
    }
    
    private void setAge() {
        Date now = new Date();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        age = (Integer.parseInt(sdf.format(now)) - birth / 10000);
    }
    
    public int getAge() {
        return age;
    }
    
    public void output() {
        System.out.println(id + "," + name + "," + gender + "," + birth + "," + age);
    }
}
