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

    public Task(String taskTitle, String taskAddress, int employeeID, int taskStatus) {
        this.taskTitle = taskTitle;
        this.taskAddress = taskAddress;
        this.employeeID = employeeID;
        this.taskStatus = taskStatus;
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

    public void setTaskId(int id) {
        taskId = id;
    }
}
