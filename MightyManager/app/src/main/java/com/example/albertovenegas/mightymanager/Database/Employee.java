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

    @ColumnInfo(name = "employee_fname")
    private String employeeFirstName;

    @ColumnInfo(name = "employee_lname")
    private String employeeLastName;

    @ColumnInfo(name = "employee_name")
    private String employeeName;

    @ColumnInfo(name = "employee_password")
    private String employeePassword;

    @ColumnInfo(name = "admin_privilege")
    private boolean admin;

    @ColumnInfo(name = "employee_username")
    private String employeeUsername;

    @ColumnInfo(name = "employee_phone")
    private String employeePhone;

    @ColumnInfo(name = "employee_email")
    private String employeeEmail;

   public Employee(String employeeFirstName, String employeeLastName, String employeePassword, boolean admin, String employeeUsername, String employeePhone, String employeeEmail) {
       this.employeeFirstName = employeeFirstName;
       this.employeeLastName = employeeLastName;
       this.employeeName = employeeFirstName.concat(" " + employeeLastName);
       this.employeePassword = employeePassword;
       this.admin = admin;
       this.employeeUsername = employeeUsername;
       this.employeePhone = employeePhone;
       this.employeeEmail = employeeEmail;
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

    public String getEmployeeUsername(){
       return this.employeeUsername;
    }

    public String getEmployeePhone() {
       return this.employeePhone;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    //should not be used
    public void setEmployeeID(int id) {
       employeeID = id;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
