package com.example.albertovenegas.mightymanager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainTaskListAdapter extends RecyclerView.Adapter<MainTaskListAdapter.taskListHolder> {
    private List<Task> tasks = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private OnItemClickListener listener;
    private String currentDateString;


    @NonNull
    @Override
    public taskListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_list_item, viewGroup, false);
        return new taskListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull taskListHolder taskListHolder, int i) {
        Task currentTask = tasks.get(i);
        taskListHolder.taskTitle.setText(currentTask.getTaskTitle());
        int status = currentTask.getTaskStatus();
        String date = currentTask.getTaskDateDue();
        long daysLeft = isTaskDueSoon(date);
        if (daysLeft < 7 && daysLeft >= 0 && status != 3) {
            taskListHolder.alertIcon.setVisibility(View.VISIBLE);
            taskListHolder.alertIcon.setColorFilter(ContextCompat.getColor(taskListHolder.itemView.getContext(), R.color.in_progress_task));
        }
        else if (daysLeft < 0 && status != 3) {
            taskListHolder.alertIcon.setVisibility(View.VISIBLE);
            taskListHolder.alertIcon.setColorFilter(ContextCompat.getColor(taskListHolder.itemView.getContext(), R.color.new_task));
        }
        else {
            taskListHolder.alertIcon.setVisibility(View.INVISIBLE);
        }
        String name;
        if (currentTask.getEmployeeID() == -999) {
            name = "Unassigned";
        }
        else {
            name = findEmployeeNameWithId(currentTask.getEmployeeID());
        }
        taskListHolder.assignedEmployee.setText(name);
        taskListHolder.appDate.setText("Date: " + date);
        //set the background color of the list items
        int color;
        switch (status) {
            case 1:
                color = R.color.new_task;
                break;
            case 2:
                color = R.color.in_progress_task;
                break;
            case 3:
                color = R.color.complete_task;
                break;
            default:
                color = R.color.main_theme;
        }
        taskListHolder.container.setBackgroundColor(ContextCompat.getColor(taskListHolder.itemView.getContext(), R.color.main_theme));
        taskListHolder.border.setBackgroundColor(ContextCompat.getColor(taskListHolder.itemView.getContext(), color));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public Task getTaskAt(int p) {
        return tasks.get(p);
    }

    class taskListHolder extends RecyclerView.ViewHolder {
        private TextView taskTitle;
        private TextView assignedEmployee;
        private TextView appDate;
        private View container;
        private View border;
        private ImageView alertIcon;

        public taskListHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.main_list_title_txt);
            assignedEmployee = itemView.findViewById(R.id.main_list_employee_txt);
            container = itemView.findViewById(R.id.main_list_root_view);
            border = itemView.findViewById(R.id.main_list_card);
            appDate = itemView.findViewById(R.id.main_list_due_date);
            alertIcon = itemView.findViewById(R.id.main_list_alert_icon);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(tasks.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private String findEmployeeNameWithId(int id) {
        String name = "employee never found";
        for(int i = 0; i < employees.size(); i++) {
            System.out.println("Searching for id: " + id);
            if (employees.get(i).getEmployeeID() == id)
            {
                System.out.println("Found match");
                name = employees.get(i).getEmployeeUsername();
            }
        }
        return name;
    }

    private long isTaskDueSoon(String dueDate) {
        //boolean dueSoon = false;
        getCurrentDate();
        long daysTotal = 0;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startingDate = new Date();
        Date endingDate = new Date();
        try {
            startingDate = df.parse(currentDateString);
            endingDate = df.parse(dueDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //long diffInMilliseconds = Math.abs(endingDate.getTime() - startingDate.getTime());
        long diffInMilliseconds = endingDate.getTime() - startingDate.getTime();
        daysTotal = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
        return daysTotal;
//        if (daysTotal < 7) {
//            dueSoon = true;
//        }
//        return dueSoon;
    }

    private void getCurrentDate() {
        Calendar currentDate = Calendar.getInstance();
        int month = currentDate.get(Calendar.MONTH) + 1;
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int year = currentDate.get(Calendar.YEAR);
        String sDay;
        String sMonth;
        if (month < 10) {
            sMonth = "0" + month;
        }
        else {
            sMonth = "" + month;
        }
        if (day < 10) {
            sDay = "0" + day;
        }
        else {
            sDay = "" + day;
        }
        currentDateString = sMonth + "/" + sDay + "/" + year;
        //Toast.makeText(AddTaskActivity.this, "current date would be: " + dateCreated, Toast.LENGTH_LONG).show();
    }
}
