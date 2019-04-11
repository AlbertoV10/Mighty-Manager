package com.example.albertovenegas.mightymanager.UserInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.R;

public class CreateOrganization extends AppCompatActivity {
    private EditText orgName;
    private EditText orgAddress;
    private EditText orgPhone;
    private EditText orgEmail;
    private Button createManagerButton;
    private String name;
    private String address;
    private String phone;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_organization);

        orgName = findViewById(R.id.create_org_name);
        orgAddress = findViewById(R.id.create_org_address);
        orgPhone = findViewById(R.id.create_org_phone);
        orgEmail = findViewById(R.id.create_org_email);
        createManagerButton = findViewById(R.id.create_org_add_manager_button);

        createManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = orgName.getText().toString();
                address = orgAddress.getText().toString();
                phone = orgPhone.getText().toString();
                email = orgEmail.getText().toString();
                openReviewInfoDialog();
                //Toast.makeText(CreateOrganization.this, "Info Verified", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openReviewInfoDialog() {
        ReviewInfoDialog reviewInfoDialog = new ReviewInfoDialog();
        Bundle bundle = new Bundle();
        bundle.putString("cName", name);
        bundle.putString("cAddress", address);
        bundle.putString("cPhone", phone);
        bundle.putString("cEmail", email);
        reviewInfoDialog.setArguments(bundle);
        reviewInfoDialog.show(getSupportFragmentManager(), "ReviewDialogInfo");

    }
}
