package com.example.albertovenegas.mightymanager.UnusedFiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListHolder>{

    private List<Assignment> listElementData;
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

    public MainListAdapter(List<Assignment> listElementData, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.listElementData = listElementData;
    }

    @NonNull
    @Override
    public MainListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view  = inflater.inflate(R.layout.main_list_item, viewGroup, false);
        return new MainListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListHolder mainListHolder, int position) {
        Assignment assignmentItem = listElementData.get(position);
        mainListHolder.assignmentTitle.setText(assignmentItem.getTitle());
        mainListHolder.employeeName.setText(assignmentItem.getAssignedEmployee());
        mainListHolder.editIcon.setImageResource(assignmentItem.getEditIcon());
        //mainListHolder.statusIcon.setImageResource(assignmentItem.getStatusIcon());
//        if(assignmentItem.isComplete())
//        {
//            mainListHolder.statusIcon.setImageResource(R.drawable.ic_assignment_complete_24dp);
//        }
//        else
//        {
//            mainListHolder.statusIcon.setImageResource(R.drawable.ic_assignment_inprogress_24dp);
//        }
    }

    public void setListData(ArrayList<Assignment> list)
    {
        this.listElementData.clear();
        this.listElementData.addAll(list);
    }

    @Override
    public int getItemCount() {
        return listElementData.size();
    }

    class MainListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView assignmentTitle;
        private TextView employeeName;
        //private ImageView statusIcon;
        private ImageView editIcon;
        private View container;


        public MainListHolder(@NonNull View itemView) {
            super(itemView);

            assignmentTitle = (TextView) itemView.findViewById(R.id.main_list_title_txt);
            employeeName = (TextView) itemView.findViewById(R.id.main_list_employee_txt);
            //statusIcon = (ImageView) itemView.findViewById(R.id.main_list_status_icon);
            //editIcon = (ImageView) itemView.findViewById(R.id.main_list_edit_icon);
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
