package com.example.albertovenegas.mightymanager.Database;

import android.arch.lifecycle.LiveData;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;


@Dao
public interface CustomerDAO {
    @Query("SELECT * FROM customer_table WHERE customer_id=:customerID")
    Customer findCustomerByID(int customerID);

    @Query("SELECT * FROM customer_table WHERE customer_name=:customerName")
    Customer findCustomerByName(String customerName);

    @Insert
    void insert(Customer customer);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("DELETE FROM customer_table")
    void deleteAllCustomers();

    @Query("SELECT * FROM customer_table ORDER BY customer_name ASC")
    LiveData<List<Customer>> getAllCustomers();

    @Query("SELECT * FROM customer_table ORDER BY customer_name ASC")
    List<Customer> getCustomersList();
}
