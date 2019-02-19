package com.example.albertovenegas.mightymanager.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "employee_table")
public class Employee {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "employee_id")
    private int employeeID;

    @ColumnInfo(name = "employee_name")
    private String employeeName;

    @ColumnInfo(name = "employee_password")
    private String employeePassword;

    @ColumnInfo(name = "admin_privilege")
    private boolean admin;

   public Employee(String employeeName, String employeePassword, boolean admin) {
       this.employeeName = employeeName;
       this.employeePassword = employeePassword;
       this.admin = admin;
   }

    public int getEmployeeID() {
        return this.employeeID;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public String getEmployeePassword() {
       return this.employeePassword;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    //should not be used
    public void setEmployeeID(int id) {
       employeeID = id;
    }
}
