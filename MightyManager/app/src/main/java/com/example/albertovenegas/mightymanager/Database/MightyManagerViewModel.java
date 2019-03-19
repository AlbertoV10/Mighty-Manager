package com.example.albertovenegas.mightymanager.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MightyManagerViewModel extends AndroidViewModel {
    private MightyManagerRepository mRepository;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Employee>> allEmployees;
    private LiveData<List<Customer>> allCustomers;

    public MightyManagerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MightyManagerRepository(application);
        allTasks = mRepository.getAllTasks();
        allEmployees = mRepository.getAllEmployees();
        allCustomers = mRepository.getAllCustomers();
    }

    // wrapper methods for Tasks
    public Task findTaskById(int taskId) {
        return mRepository.findTaskById(taskId);
    }

    public List<Task> findTaskByEmployee(int employeeId) {
        return mRepository.findTaskByEmployee(employeeId);
    }

    public List<Task> findTaskByCustomer(int customerId) {
        return mRepository.findTaskByCustomer(customerId);
    }

    public void insert(Task task) {
        mRepository.insert(task);
    }

    public void update(Task task) {
        mRepository.update(task);
    }

    public void delete(Task task) {
        mRepository.delete(task);
    }

    public void deleteAllTasks() {
        mRepository.deleteAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public List<Task> getTaskList() {
        return mRepository.getTaskList();
    }

    // wrapper methods for Employees
    public Employee findEmployeeByName(String name) {
        return mRepository.findEmployeeByName(name);
    }

    public Employee findEmployeeByUsername(String username) {
        return mRepository.findEmployeeByUsername(username);
    }

    public Employee findEmployeeById(int id) {
        return mRepository.findEmployeeById(id);
    }

    public void insert(Employee employee) {
        mRepository.insert(employee);
    }

    public void update(Employee employee) {
        mRepository.update(employee);
    }

    public void delete(Employee employee) {
        mRepository.delete(employee);
    }

    public void deleteAllEmployees() {
        mRepository.deleteAllEmployees();
    }

    public LiveData<List<Employee>> getAllEmployees() {
        return allEmployees;
    }

    public List<Employee> getEmployeesList() {
        return mRepository.getEmployeesList();
    }

    // wrapper methods for customer
    public Customer findCustomerByName(String name) {
        return mRepository.findCustomerByName(name);
    }

    public Customer findCustomerById(int id) {
        return mRepository.findCustomerById(id);
    }

    public void insert(Customer customer) {
        mRepository.insert(customer);
    }
    public void update(Customer customer) {
        mRepository.update(customer);
    }

    public void delete(Customer customer) {
        mRepository.delete(customer);
    }

    public void deleteAllCustomers() {
        mRepository.deleteAllCustomers();
    }

    public LiveData<List<Customer>> getAllCustomers() {
        return allCustomers;
    }

    public List<Customer> getCustomerList() {
        return mRepository.getCustomerList();
    }
}
