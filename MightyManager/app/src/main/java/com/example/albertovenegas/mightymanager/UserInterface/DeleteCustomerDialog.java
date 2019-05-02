package com.example.albertovenegas.mightymanager.UserInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;

public class DeleteCustomerDialog extends AppCompatDialogFragment {
    private MightyManagerViewModel mmvm;
    private String cName;
    private int cId;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        if (getArguments() != null) {
            cName = getArguments().getString("cName");
            cId = getArguments().getInt("cId");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Customer")
                .setMessage("You are about to delete the customer:\n " +
                        cName + "\nDo you wish to continue?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Customer customer = mmvm.findCustomerById(cId);
                        mmvm.delete(customer);
                        Intent intent = new Intent(getActivity(), CustomerList.class);
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
