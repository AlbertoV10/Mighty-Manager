package com.example.albertovenegas.mightymanager.Data;

public class Assignment {
//    private enum Status {
//        OPEN,
//        INPROGRESS,
//        CLOSED
//    }
    private String title;
    private String address;
    //private Employee assignedEmployee;
    private String assignedEmployee;
    //private Status status;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }
}
