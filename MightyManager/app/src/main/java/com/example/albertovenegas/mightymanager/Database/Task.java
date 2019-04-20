package com.example.albertovenegas.mightymanager.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table")
public class Task {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int taskId;

    @ColumnInfo(name = "task_title")
    private String taskTitle;

    @ColumnInfo(name = "task_address")
    private String taskAddress;

    @ColumnInfo(name = "assigned_employee")
    private int employeeID;

    @ColumnInfo(name = "status")
    private int taskStatus;

    @ColumnInfo(name = "task_customer")
    private int customerID;

    @ColumnInfo(name = "task_description")
    private String taskDescription;

    @ColumnInfo(name = "task_date_created")
    private String taskDateCreated;

    @ColumnInfo(name = "task_date_due")
    private String taskDateDue;

    @ColumnInfo(name = "task_date_time")
    private String taskAppTime;

    public Task(String taskTitle, String taskAddress, int employeeID, int taskStatus, int customerID, String taskDescription, String taskDateCreated, String taskDateDue, String taskAppTime) {
        this.taskTitle = taskTitle;
        this.taskAddress = taskAddress;
        this.employeeID = employeeID;
        this.taskStatus = taskStatus;
        this.customerID = customerID;
        this.taskDescription = taskDescription;
        this.taskDateCreated = taskDateCreated;
        this.taskDateDue = taskDateDue;
        this.taskAppTime = taskAppTime;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public String getTaskAddress() {
        return this.taskAddress;
    }

    public int getEmployeeID() {
        return this.employeeID;
    }

    public int getTaskStatus() {
        return this.taskStatus;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskDateCreated() {
        return taskDateCreated;
    }

    public String getTaskDateDue() {
        return taskDateDue;
    }

    public String getTaskAppTime() {
        return taskAppTime;
    }

    public void setTaskId(int id) {
        taskId = id;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskDateCreated(String taskDateCreated) {
        this.taskDateCreated = taskDateCreated;
    }

    public void setTaskDateDue(String taskDateDue) {
        this.taskDateDue = taskDateDue;
    }

    public void setTaskAppTime(String taskAppTime) {
        this.taskAppTime = taskAppTime;
    }
}
