package com.example.albertovenegas.mightymanager.UserInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Organization;

import java.util.List;

public class ReviewInfoDialog extends AppCompatDialogFragment {
    private MightyManagerViewModel mmvm;
    private String name;
    private String address;
    private String phone;
    private String email;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        if (getArguments() != null) {
            name = getArguments().getString("cName");
            address = getArguments().getString("cAddress");
            phone = getArguments().getString("cPhone");
            email = getArguments().getString("cEmail");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Review Information")
                .setMessage("Please verify the information entered is correct: \nName: "
                + name + "\nAddress: " + address
                + "\nPhone: " + phone + "\nEmail: " + email).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //create the org here
                Intent createManager = new Intent(getActivity(), CreateEmployee.class);
                createManager.putExtra("fromCreateOrg", true);
                startActivity(createManager);
                Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
