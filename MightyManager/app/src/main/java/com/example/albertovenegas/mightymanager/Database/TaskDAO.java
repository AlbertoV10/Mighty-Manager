package com.example.albertovenegas.mightymanager.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM task_table WHERE task_id=:taskId")
    Task findTaskById(int taskId);

    @Query("SELECT * FROM task_table WHERE task_title=:taskTitle")
    Task findTaskByTitle(String taskTitle);

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task_table")
    void deleteAllTasks();

    @Query("SELECT * FROM task_table ORDER BY task_title ASC")
    LiveData<List<Task>> getAllTasks();
}
