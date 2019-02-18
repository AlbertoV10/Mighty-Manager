package com.example.albertovenegas.mightymanager.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Task.class, Employee.class}, version = 1)
public abstract class MightyManagerDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Mighty_Manager_Database";

    public abstract EmployeeDAO employeeDAO();
    public abstract TaskDAO taskDAO();

    private static volatile MightyManagerDatabase INSTANCE;

    static MightyManagerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MightyManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MightyManagerDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();                }
            }
        }
        return INSTANCE;
    }

    //this code is used to populate some intial database data
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDAO taskDAO;
        private EmployeeDAO employeeDAO;

        private PopulateDatabaseAsyncTask(MightyManagerDatabase database) {
            this.taskDAO = database.taskDAO();
            this.employeeDAO = database.employeeDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            taskDAO.insert(new Task("Test Task", "123 Main St.", 0000, 1));
            employeeDAO.insert(new Employee("Test Employee", false));
            employeeDAO.insert(new Employee("Test Manager", true));
            return null;
        }
    }
}
