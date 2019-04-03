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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainTaskListAdapter extends RecyclerView.Adapter<MainTaskListAdapter.taskListHolder> {
    private List<Task> tasks = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private OnItemClickListener listener;


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
        String date = currentTask.getTaskDateDue();
        String name;
        if (currentTask.getEmployeeID() == -999) {
            name = "Unassigned";
        }
        else {
            name = findEmployeeNameWithId(currentTask.getEmployeeID());
        }
        taskListHolder.assignedEmployee.setText(name);
        taskListHolder.dueDate.setText("Due Date: " + date);
        //set the background color of the list items
        int color;
        int status = currentTask.getTaskStatus();
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
        private TextView dueDate;
        private View container;
        private View border;
        private ImageView alertIcon;

        public taskListHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.main_list_title_txt);
            assignedEmployee = itemView.findViewById(R.id.main_list_employee_txt);
            container = itemView.findViewById(R.id.main_list_root_view);
            border = itemView.findViewById(R.id.main_list_card);
            dueDate = itemView.findViewById(R.id.main_list_due_date);
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

    private boolean isTaskDueSoon(String dueDate) {
        boolean dueSoon = false;
        Calendar currentDate = Calendar.getInstance();
        String todaysDate = currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH) + "/" + currentDate.get(Calendar.YEAR);
        //get int values of current date
        int currentYear = 0;
        int currentMonth = 0;
        int currentDay = 0;
        boolean firstSlashSeen = true;
        int firstSlashIndex = 0;
        for (int i = 0; i < todaysDate.length(); i++)
        {
            if (todaysDate.charAt(i) == '/' && firstSlashSeen) {
                firstSlashIndex = i;
                currentMonth = Integer.parseInt(todaysDate.substring(0, i));
                firstSlashSeen = false;
            }
            if (todaysDate.charAt(i) == '/' && !firstSlashSeen) {
                currentDay = Integer.parseInt(todaysDate.substring(firstSlashIndex, i));
                currentYear = Integer.parseInt(todaysDate.substring(i, todaysDate.length()-1));
            }
        }
        int dueYear = 0;
        int dueMonth = 0;
        int dueDay = 0;
        firstSlashSeen = true;
        firstSlashIndex = 0;
        for (int i = 0; i < dueDate.length(); i++)
        {
            if (dueDate.charAt(i) == '/' && firstSlashSeen) {
                firstSlashIndex = i;
                dueMonth = Integer.parseInt(dueDate.substring(0, i));
                firstSlashSeen = false;
            }
            if (dueDate.charAt(i) == '/' && !firstSlashSeen) {
                dueDay = Integer.parseInt(dueDate.substring(firstSlashIndex, i));
                dueYear = Integer.parseInt(dueDate.substring(i, todaysDate.length()-1));
            }
        }
//        int yearDiff = Math.abs(dueYear - currentYear);
//        int monthDiff = Math.abs(dueMonth - currentMonth);
//        int dayDiff = Math.abs(dueDay - currentMonth);

        //if ((dueMonth - currentMonth == 0 && (dueDay - currentDay == )))

        return dueSoon;
    }
}
