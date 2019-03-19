package com.example.albertovenegas.mightymanager.UserInterface;

import android.content.Context;
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

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ListHolder>{
    private List<Employee> employeeList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.employee_list_item, viewGroup, false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder listHolder, int i) {
        Employee currentEmployee = employeeList.get(i);
        listHolder.employeeName.setText(currentEmployee.getEmployeeName());
        listHolder.employeeUsername.setText(currentEmployee.getEmployeeUsername());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    public List<Employee> getEmployeeList() {
        return this.employeeList;
    }

    public Employee employeeAt(int p) {
        return employeeList.get(p);
    }

    public EmployeeListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    class ListHolder extends RecyclerView.ViewHolder {
        private TextView employeeName;
        private TextView employeeUsername;
        private View container;

        public ListHolder(View view) {
            super(view);
            employeeName = view.findViewById(R.id.employee_list_item_name);
            employeeUsername = view.findViewById(R.id.employee_list_item_username);
            container = view.findViewById(R.id.employee_list_item_root);
            //container.setOnClickListener(this);
        }

    }
}
