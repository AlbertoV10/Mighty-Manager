package com.example.albertovenegas.mightymanager.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.employeeListHolder> implements Filterable {
    private List<Employee> employeeList = new ArrayList<>();
    private List<Task> tasksList = new ArrayList<>();
    private OnItemClickListener listener;
    private List<Employee> allEmployees;

    @NonNull
    @Override
    public employeeListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.employee_list_item, viewGroup, false);
        return new employeeListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull employeeListHolder employeeListHolder, int i) {
        Employee currentEmployee = employeeList.get(i);
        employeeListHolder.eName.setText(currentEmployee.getEmployeeName());
        employeeListHolder.eDescription.setText(currentEmployee.getEmployeeDescription());
        int totalTasks = setTotalTasks(currentEmployee);
        employeeListHolder.eTotalTasks.setText(String.valueOf(totalTasks));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        allEmployees = new ArrayList<>(employeeList);
        notifyDataSetChanged();
    }

    public void setTasks(List<Task> tasksList) {
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Employee> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allEmployees);
            }
            else {
                String searchPattern = constraint.toString().toLowerCase().trim();

                for (Employee employee : allEmployees) {
                    if (employee.getEmployeeName().toLowerCase().contains(searchPattern)) {
                        filteredList.add(employee);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            employeeList.clear();
            employeeList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class employeeListHolder extends RecyclerView.ViewHolder {
        private TextView eName;
        private TextView eDescription;
        private TextView eTotalTasks;
        private View totalTasksBox;

        public employeeListHolder(@NonNull View itemView) {
            super(itemView);
            eName = itemView.findViewById(R.id.employee_list_item_name);
            eDescription = itemView.findViewById(R.id.employee_list_item_description);
            eTotalTasks = itemView.findViewById(R.id.employee_list_item_total_tasks);
            totalTasksBox = itemView.findViewById(R.id.employee_list_active_tasks_root);

            totalTasksBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onTotalTasksClicked(employeeList.get(position));
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemCLick(employeeList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemCLick(Employee employee);
        void onTotalTasksClicked(Employee employee);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private int setTotalTasks(Employee employee) {
        int id = employee.getEmployeeID();
        int totalTasks = 0;
        for (int i = 0; i < tasksList.size(); i++) {
            if (tasksList.get(i).getEmployeeID() == id && (tasksList.get(i).getTaskStatus() == 1 || tasksList.get(i).getTaskStatus() == 2))
            {
                totalTasks++;
            }
        }
        return totalTasks;
    }
}
