package com.example.albertovenegas.mightymanager.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.employeeListHolder> {
    private List<Employee> employeeList = new ArrayList<>();
    private OnItemClickListener listener;

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
        employeeListHolder.eUsername.setText(currentEmployee.getEmployeeUsername());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    class employeeListHolder extends RecyclerView.ViewHolder {
        private TextView eName;
        private TextView eUsername;

        public employeeListHolder(@NonNull View itemView) {
            super(itemView);
            eName = itemView.findViewById(R.id.employee_list_item_name);
            eUsername = itemView.findViewById(R.id.employee_list_item_username);

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
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
