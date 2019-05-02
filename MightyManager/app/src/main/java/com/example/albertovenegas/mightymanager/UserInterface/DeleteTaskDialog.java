package com.example.albertovenegas.mightymanager.UserInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;

public class DeleteTaskDialog extends AppCompatDialogFragment {
    private MightyManagerViewModel mmvm;
    private int taskId;
    private int employeeId; //employee deleting task
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        if (getArguments() != null) {
            taskId = getArguments().getInt("taskId");
            employeeId = getArguments().getInt("user");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Task taskToDelete = mmvm.findTaskById(taskId);
                        mmvm.delete(taskToDelete);
                        Intent intent = new Intent(getActivity(), MainAppScreen.class);
                        intent.putExtra("user", employeeId);
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }
}
