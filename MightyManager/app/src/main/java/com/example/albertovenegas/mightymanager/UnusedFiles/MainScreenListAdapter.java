
package com.example.albertovenegas.mightymanager.UnusedFiles;

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

public class MainScreenListAdapter extends RecyclerView.Adapter<MainScreenListAdapter.MainScreenListHolder>{

    private List<Task> listElementData = new ArrayList<>();
    private LayoutInflater inflater;

    private itemClickCallback itemClickCallback;

    public interface itemClickCallback {
        void onItemClick(int p);
        void onEditIconClick(int p);
    }

    public void setItemClickCallback(final itemClickCallback itemClickCallback)
    {
        this.itemClickCallback = itemClickCallback;
    }

    public MainScreenListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        //this.listElementData = listElementData;
    }

    @NonNull
    @Override
    public MainScreenListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view  = inflater.inflate(R.layout.main_list_item, viewGroup, false);
        return new MainScreenListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainScreenListHolder mainListHolder, int position) {
        Task taskItem = listElementData.get(position);
        mainListHolder.taskTitle.setText(taskItem.getTaskTitle());
        mainListHolder.assignedEmployee.setText(taskItem.getEmployeeID());
        mainListHolder.editIcon.setImageResource(android.R.drawable.ic_menu_edit);
        //mainListHolder.statusIcon.setImageResource(assignmentItem.getStatusIcon());
//        if(taskItem.getTaskStatus() == 2)
//        {
//            mainListHolder.statusIcon.setImageResource(R.drawable.ic_assignment_complete_24dp);
//        }
//        else
//        {
//            mainListHolder.statusIcon.setImageResource(R.drawable.ic_assignment_inprogress_24dp);
//        }
    }

    public void setListData(List<Task> list)
    {
        this.listElementData.clear();
        this.listElementData.addAll(list);
    }

    @Override
    public int getItemCount() {
        return listElementData.size();
    }

    class MainScreenListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView taskTitle;
        private TextView assignedEmployee;
        //private ImageView statusIcon;
        private ImageView editIcon;
        private View container;

        public MainScreenListHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.main_list_title_txt);
            assignedEmployee = itemView.findViewById(R.id.main_list_employee_txt);
            //statusIcon = itemView.findViewById(R.id.main_list_status_icon);
            //editIcon = itemView.findViewById(R.id.main_list_edit_icon);
            editIcon.setOnClickListener(this);
            container = itemView.findViewById(R.id.main_list_root_view);
            container.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.main_list_root_view)
            {
                itemClickCallback.onItemClick(getAdapterPosition());
            }
            else
            {
                itemClickCallback.onEditIconClick(getAdapterPosition());
            }
        }
    }
}
