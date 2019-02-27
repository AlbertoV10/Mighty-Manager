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

    public MightyManagerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MightyManagerRepository(application);
        allTasks = mRepository.getAllTasks();
        allEmployees = mRepository.getAllEmployees();
    }

    // wrapper methods for Tasks
    public Task findTaskById(int taskId) {
        return mRepository.findTaskById(taskId);
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

    // wrapper methods for Employees
    public Employee findEmployeeByName(String name) {
        return mRepository.findEmployeeByName(name);
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
}
