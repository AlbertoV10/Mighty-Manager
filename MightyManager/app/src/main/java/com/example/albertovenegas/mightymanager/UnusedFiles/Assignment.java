package com.example.albertovenegas.mightymanager.UnusedFiles;

public class Assignment {

    private String title;
    private String address;
    private String assignedEmployee;
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
