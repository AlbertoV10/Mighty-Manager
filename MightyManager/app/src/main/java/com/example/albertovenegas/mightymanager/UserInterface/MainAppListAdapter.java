package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class MainAppListAdapter extends RecyclerView.Adapter<MainAppListAdapter.ListHolder> {
    private List<Task> tasks = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private LayoutInflater inflater;
    private itemClickCallback itemClickCallback;


    //interface for click callback
    public interface itemClickCallback {
        void onItemClick(int p);
        void onIconClick(int p);
    }

    public void setItemClickCallback(final itemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public MainAppListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_item, viewGroup, false);
        View itemView = inflater.inflate(R.layout.main_list_item, viewGroup, false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder listHolder, int i) {
        Task currentTask = tasks.get(i);
        listHolder.taskTitle.setText(currentTask.getTaskTitle());
        String name = findEmployeeNameWithId(currentTask.getEmployeeID());
        listHolder.assignedEmployee.setText(name);
        //listHolder.assignedEmployee.setText(String.valueOf(currentTask.getEmployeeID()));
        listHolder.editIcon.setImageResource(android.R.drawable.ic_menu_edit);
//        if (currentTask.getTaskStatus() == 2) {
//            listHolder.statusIcon.setImageResource(R.drawable.ic_assignment_complete_24dp);
//        } else {
//            listHolder.statusIcon.setImageResource(R.drawable.ic_assignment_inprogress_24dp);
//        }
        if (currentTask.getTaskStatus() == 1) {
            listHolder.container.setBackgroundColor(ContextCompat.getColor(listHolder.itemView.getContext(), R.color.new_task));
        }
        else if (currentTask.getTaskStatus() == 2) {
            listHolder.container.setBackgroundColor(ContextCompat.getColor(listHolder.itemView.getContext(), R.color.in_progress_task));
        }
        else if (currentTask.getTaskStatus() == 3) {
            listHolder.container.setBackgroundColor(ContextCompat.getColor(listHolder.itemView.getContext(), R.color.complete_task));
        }
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

    class ListHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView taskTitle;
        private TextView assignedEmployee;
        //private ImageView statusIcon;
        private ImageView editIcon;
        private View container;

        public ListHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.main_list_title_txt);
            assignedEmployee = itemView.findViewById(R.id.main_list_employee_txt);
            //statusIcon = itemView.findViewById(R.id.main_list_status_icon);
            editIcon = itemView.findViewById(R.id.main_list_edit_icon);
            //click listener
            editIcon.setOnClickListener(this);
            container = itemView.findViewById(R.id.main_list_root_view);
            container.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.main_list_root_view) {
                itemClickCallback.onItemClick(getAdapterPosition());
            }
            else {
                itemClickCallback.onIconClick(getAdapterPosition());
            }
        }
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
