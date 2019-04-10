package com.example.albertovenegas.mightymanager.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface OrganizationDAO {
    @Query("SELECT * FROM organization_table WHERE organization_id=:organizationId")
    Organization findOrganizationById(int organizationId);

    @Query("SELECT * FROM organization_table WHERE organization_name=:organizationName")
    Organization findOrganizationByName(String organizationName);

    @Insert
    void insert(Organization organization);

    @Update
    void update(Organization organization);

    @Delete
    void delete(Organization organization);

    @Query("DELETE FROM organization_table")
    void deleteAllOrganizations();

    @Query("SELECT * FROM organization_table ORDER BY organization_name ASC")
    List<Organization> getOrganizations();
}
