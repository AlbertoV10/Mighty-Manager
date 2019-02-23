package com.example.albertovenegas.mightymanager.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MightyManagerRepository {
    private TaskDAO taskDAO;
    private EmployeeDAO employeeDAO;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Employee>> allEmployees;
    private List<Employee> employeeList;

    MightyManagerRepository(Application application) {
        MightyManagerDatabase mmdb = MightyManagerDatabase.getDatabase(application);
        taskDAO = mmdb.taskDAO();
        employeeDAO = mmdb.employeeDAO();
        allTasks = taskDAO.getAllTasks();
        allEmployees = employeeDAO.getAllEmployees();
        employeeList = employeeDAO.getEmployeesList();
    }

    // methods for TaskDAO
    public void insert(Task task) {
        new InsertTaskAsyncTask(taskDAO).execute(task);
    }

    public void update(Task task) {
        new UpdateTaskAsyncTask(taskDAO).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDAO).execute(task);
    }

    public void deleteAllTasks() {
        new DeleteAllTasksAsyncTask(taskDAO).execute();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    //Async classes for TaskDAO
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO taskDAO;

        private InsertTaskAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDAO.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO taskDAO;

        private UpdateTaskAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDAO.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO taskDAO;

        private DeleteTaskAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDAO.delete(tasks[0]);
            return null;
        }
    }

    private static class DeleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDAO taskDAO;

        private DeleteAllTasksAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDAO.deleteAllTasks();
            return null;
        }
    }

    // methods for EmployeeDAO
    public Employee findEmployeeByName(String employeeName) {
        return employeeDAO.findEmployeeByName(employeeName);
    }

    public Employee findEmployeeById(int employeeId) {
        return employeeDAO.findEmployeeById(employeeId);
    }

    public void insert(Employee employee) {
        new InsertEmployeeAsyncTask(employeeDAO).execute(employee);
    }

    public void update(Employee employee) {
        new UpdateEmployeeAsyncTask(employeeDAO).execute(employee);
    }

    public void delete(Employee employee) {
        new DeleteEmployeeAsyncTask(employeeDAO).execute(employee);
    }

    public void deleteAllEmployees() {
        new DeleteAllEmployeesAsyncTask(employeeDAO).execute();
    }

    public LiveData<List<Employee>> getAllEmployees() {
        return allEmployees;
    }

    public List<Employee> getEmployeesList() {
        return employeeList;
    }

    //Async classes for EmployeeDAO
    private static class InsertEmployeeAsyncTask extends AsyncTask<Employee, Void, Void> {
        private EmployeeDAO employeeDAO;

        private InsertEmployeeAsyncTask(EmployeeDAO employeeDAO) {
            this.employeeDAO = employeeDAO;
        }

        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDAO.insert(employees[0]);
            return null;
        }
    }

    private static class UpdateEmployeeAsyncTask extends AsyncTask<Employee, Void, Void> {
        private EmployeeDAO employeeDAO;

        private UpdateEmployeeAsyncTask(EmployeeDAO employeeDAO) {
            this.employeeDAO = employeeDAO;
        }

        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDAO.update(employees[0]);
            return null;
        }
    }

    private static class DeleteEmployeeAsyncTask extends AsyncTask<Employee, Void, Void> {
        private EmployeeDAO employeeDAO;

        private DeleteEmployeeAsyncTask(EmployeeDAO employeeDAO) {
            this.employeeDAO = employeeDAO;
        }

        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDAO.delete(employees[0]);
            return null;
        }
    }

    private static class DeleteAllEmployeesAsyncTask extends AsyncTask<Void, Void, Void> {
        private EmployeeDAO employeeDAO;

        private DeleteAllEmployeesAsyncTask(EmployeeDAO employeeDAO) {
            this.employeeDAO = employeeDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            employeeDAO.deleteAllEmployees();
            return null;
        }
    }
}
