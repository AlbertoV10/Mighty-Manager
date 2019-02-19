package com.example.albertovenegas.mightymanager.TestMainScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class MainAppListAdapter extends RecyclerView.Adapter<MainAppListAdapter.ListHolder> {
    private List<Task> tasks = new ArrayList<>();

    private LayoutInflater inflater;

    public MainAppListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        //this.listElementData = listElementData;
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
        listHolder.assignedEmployee.setText(String.valueOf(currentTask.getEmployeeID()));
        listHolder.editIcon.setImageResource(android.R.drawable.ic_menu_edit);
        if (currentTask.getTaskStatus() == 2) {
            listHolder.statusIcon.setImageResource(R.drawable.ic_assignment_complete_24dp);
        } else {
            listHolder.statusIcon.setImageResource(R.drawable.ic_assignment_inprogress_24dp);
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

    class ListHolder extends RecyclerView.ViewHolder {
        private TextView taskTitle;
        private TextView assignedEmployee;
        private ImageView statusIcon;
        private ImageView editIcon;

        public ListHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.main_list_title_txt);
            assignedEmployee = itemView.findViewById(R.id.main_list_employee_txt);
            statusIcon = itemView.findViewById(R.id.main_list_status_icon);
            editIcon = itemView.findViewById(R.id.main_list_edit_icon);
        }
    }
}
