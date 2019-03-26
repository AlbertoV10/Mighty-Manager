package com.example.albertovenegas.mightymanager.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
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
        String name;
        if (currentTask.getEmployeeID() == -999) {
            name = "Unassigned";
        }
        else {
            name = findEmployeeNameWithId(currentTask.getEmployeeID());
        }
        taskListHolder.assignedEmployee.setText(name);
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
        taskListHolder.container.setBackgroundColor(ContextCompat.getColor(taskListHolder.itemView.getContext(), color));
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
        private View container;

        public taskListHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.main_list_title_txt);
            assignedEmployee = itemView.findViewById(R.id.main_list_employee_txt);
            container = itemView.findViewById(R.id.main_list_root_view);

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
}
