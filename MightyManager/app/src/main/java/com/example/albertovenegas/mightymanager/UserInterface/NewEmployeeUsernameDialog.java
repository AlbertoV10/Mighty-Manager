package com.example.albertovenegas.mightymanager.UserInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class NewEmployeeUsernameDialog extends AppCompatDialogFragment {
    private String fName;
    private String lName;
    private String username;
    private String password;
    private int managerId;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            fName = getArguments().getString("eFName");
            lName = getArguments().getString("eLName");
            username = getArguments().getString("eUsername");
            password = getArguments().getString("ePassword");
            managerId = getArguments().getInt("managerID");

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New Employee Details")
                .setMessage("Employee: " + fName + " " + lName +
                        "\nUsername: " + username + "\nTemporary Password: " + password)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), MainAppScreen.class);
                        intent.putExtra("user", managerId);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
