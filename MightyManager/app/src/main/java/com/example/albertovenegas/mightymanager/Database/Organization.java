package com.example.albertovenegas.mightymanager.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "organization_table")
public class Organization {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "organization_id")
    private int organizationID;

    @ColumnInfo(name = "organization_name")
    private String organizationName;

    @ColumnInfo(name = "organization_address")
    private String organizationAddress;

    @ColumnInfo(name = "organization_phone")
    private String organizationPhone;

    @ColumnInfo(name = "organization_email")
    private String organizationEmail;

    @ColumnInfo(name = "organization_manager")
    private int managerID;

    @ColumnInfo(name = "number_of_managers")
    private int numberOfManagers;

    @ColumnInfo(name = "number_of_employees")
    private int numberOfEmployees;

    public Organization(String organizationName, String organizationAddress, String organizationPhone, String organizationEmail, int managerID, int numberOfManagers, int numberOfEmployees) {
        this.organizationName = organizationName;
        this.organizationAddress = organizationAddress;
        this.organizationPhone = organizationPhone;
        this.organizationEmail = organizationEmail;
        this.managerID = managerID;
        this.numberOfManagers = numberOfManagers;
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationPhone() {
        return organizationPhone;
    }

    public void setOrganizationPhone(String organizationPhone) {
        this.organizationPhone = organizationPhone;
    }

    public String getOrganizationEmail() {
        return organizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getNumberOfManagers() {
        return numberOfManagers;
    }

    public void setNumberOfManagers(int numberOfManagers) {
        this.numberOfManagers = numberOfManagers;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }
}
