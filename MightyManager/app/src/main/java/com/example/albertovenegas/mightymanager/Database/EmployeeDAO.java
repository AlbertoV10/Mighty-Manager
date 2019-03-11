package com.example.albertovenegas.mightymanager.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface EmployeeDAO {
    @Query("SELECT * FROM employee_table WHERE employee_id=:employeeId")
    Employee findEmployeeById(int employeeId);

    @Query("SELECT * FROM employee_table WHERE employee_name=:employeeName")
    Employee findEmployeeByName(String employeeName);

    @Query("SELECT * FROM employee_table WHERE employee_password=:employeePassword")
    Employee findEmployeeByPassword(String employeePassword);

    @Query("SELECT * FROM employee_table WHERE employee_username=:employeeUsername")
    Employee findEmployeeByUsername(String employeeUsername);

    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);

    @Query("DELETE FROM employee_table")
    void deleteAllEmployees();

    @Query("SELECT * FROM employee_table ORDER BY employee_name ASC")
    LiveData<List<Employee>> getAllEmployees();

    @Query("SELECT * FROM employee_table ORDER BY employee_name ASC")
    List<Employee> getEmployeesList();
}
