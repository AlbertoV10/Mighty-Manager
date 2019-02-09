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
    private int statusIcon;
    private int editIcon;
    private boolean complete;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getEditIcon() {
        return editIcon;
    }

    public void setEditIcon(int editIcon) {
        this.editIcon = editIcon;
    }

    public int getStatusIcon() {
        return statusIcon;
    }

    public void setStatusIcon(int status) {
        this.statusIcon = status;
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
