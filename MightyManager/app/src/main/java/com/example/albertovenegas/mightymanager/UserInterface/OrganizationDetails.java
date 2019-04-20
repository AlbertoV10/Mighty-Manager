package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Organization;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class OrganizationDetails extends AppCompatActivity {
    private TextView oName;
    private TextView oAddress;
    private TextView oPhone;
    private TextView oEmail;
    private TextView mName;
    private TextView mPhone;
    private TextView mEmail;
    private Employee manager;
    private List<Organization> organizationsList;
    private Organization organization;

    private MightyManagerViewModel mmvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("");

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
        organizationsList = mmvm.getOrganizations();
        if (organizationsList.size() != 0) {
            organization = organizationsList.get(0);
        }
        int managerId = organization.getManagerID();
        manager = mmvm.findEmployeeById(managerId);


        oName = findViewById(R.id.org_details_name);
        oAddress = findViewById(R.id.org_details_address);
        oPhone = findViewById(R.id.org_details_phone);
        oEmail = findViewById(R.id.org_details_email);
        mName = findViewById(R.id.org_details_manager_name);
        mPhone = findViewById(R.id.org_details_manager_phone);
        mEmail = findViewById(R.id.org_details_manager_email);

        oName.setText(organization.getOrganizationName());
        oAddress.setText(organization.getOrganizationAddress());
        oPhone.setText(organization.getOrganizationPhone());
        oEmail.setText(organization.getOrganizationEmail());
        mName.setText(manager.getEmployeeName());
        mPhone.setText(manager.getEmployeePhone());
        mEmail.setText(manager.getEmployeeEmail());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cancelScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void cancelScreen() {
        finish();
    }
}
