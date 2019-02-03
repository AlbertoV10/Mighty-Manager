package com.example.albertovenegas.mightymanager.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.R;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListHolder>{


    @NonNull
    @Override
    public MainListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MainListHolder mainListHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MainListHolder extends RecyclerView.ViewHolder {
        private TextView assignmentTitle;
        private TextView employeeName;
        private ImageView statusIcon;
        private ImageView editIcon;


        public MainListHolder(@NonNull View itemView) {
            super(itemView);

            assignmentTitle = (TextView) itemView.findViewById(R.id.main_list_title_txt);
            employeeName = (TextView) itemView.findViewById(R.id.main_list_employee_txt);
            statusIcon = (ImageView) itemView.findViewById(R.id.main_list_status_icon);
            editIcon = (ImageView) itemView.findViewById(R.id.main_list_edit_icon);
        }
    }
}
