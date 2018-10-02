/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_db.data;

/**
 *
 * @author 佐藤孝史
 */
public class StaffMaster {
    
    public static final int MAX_COLUMN = 4;
    private int id;
    private String name;
    private String gender;
    private int birth;
    
    public StaffMaster(int id, String name, String gender, int birth) {
        
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }
    
    public int getID() {
        return this.id;
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
}
