package com.example.albertovenegas.mightymanager.UserInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class NewMainManagerUsernameDialog extends AppCompatDialogFragment {
    private String username;
    private String password;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            username = getArguments().getString("mUsername");
            password = getArguments().getString("mPassword");

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login Details")
                .setMessage("\nUsername: " + username + "\nPassword: " + password)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent signinScreenIntent = new Intent(getActivity(), SignInPage.class);
                        signinScreenIntent.putExtra("managerUserNameForFirstTime", username);
                        startActivity(signinScreenIntent);
                    }
                });
        return builder.create();
    }
}
