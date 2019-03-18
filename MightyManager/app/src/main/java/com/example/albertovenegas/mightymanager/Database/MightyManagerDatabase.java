package com.example.albertovenegas.mightymanager.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(entities = {Task.class, Employee.class, Customer.class}, version = 6)
public abstract class MightyManagerDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Mighty_Manager_Database";

    public abstract EmployeeDAO employeeDAO();
    public abstract TaskDAO taskDAO();
    public abstract CustomerDAO customerDAO();
    public EmployeeDAO employeeDAO;


    private static volatile MightyManagerDatabase INSTANCE;

    static MightyManagerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MightyManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MightyManagerDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback).allowMainThreadQueries()
                            .build();                }
            }
        }
        return INSTANCE;
    }

    //this code is used to populate some intial database data
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull final SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDAO taskDAO;
        private EmployeeDAO employeeDAO;
        private CustomerDAO customerDAO;

        private PopulateDatabaseAsyncTask(MightyManagerDatabase database) {
            this.taskDAO = database.taskDAO();
            this.employeeDAO = database.employeeDAO();
            this.customerDAO = database.customerDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            taskDAO.insert(new Task("Test Task 1", "123 Main St.", 1234, 1));
//            taskDAO.insert(new Task("Test Task 2", "456 Main St.", 5678, 1));
//            taskDAO.insert(new Task("Test Task 3", "789 Main St.", 9123, 1));
            employeeDAO.insert(new Employee("manager", "admin", "manageradmin", true, "madmin", "(800)555-1234"));
            employeeDAO.insert(new Employee("employee", "admin", "employeeadmin", false, "eadmin", "(800)555-5678"));
            //taskDAO.insert(new Task("Test Task" + 1, 123 + " main", -999, 1, customer.getCustomerID(), "This is a test task for manager"));
            System.out.println("Created employees");
//
//            employeeDAO.insert(new Employee("employee", "employeepassword", false));
//            employeeDAO.insert(new Employee("manager","manageradmin", true));
            return null;
        }
    }
}
