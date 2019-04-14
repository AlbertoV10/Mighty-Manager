package com.example.albertovenegas.mightymanager.UserInterface;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

public class CustomerDetails extends AppCompatActivity {
    public static final int REQUEST_CALL = 1;

    private EditText cName;
    private EditText cPhone;
    private EditText cEmail;

    private String currentName;
    private String currentPhone;
    private String currentEmail;

    private MightyManagerViewModel mmvm;
    private Customer currentCustomer;
    ColorStateList originalTextColor;
    private Menu menu;
    private boolean editable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        //prevent keyboard from opening at start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        getSupportActionBar().setTitle("");

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
        final int customerId = getIntent().getExtras().getInt(CustomerList.CUSTOMER_DESCRIPTION_EXTRA_KEY);
        currentCustomer = mmvm.findCustomerById(customerId);

        cName = findViewById(R.id.customer_details_name);
        cPhone = findViewById(R.id.customer_details_phone);
        cEmail = findViewById(R.id.customer_details_email);

        //populate views and disable editing
        originalTextColor = cPhone.getTextColors();
        cName.setText(currentCustomer.getCustomerName());
        cName.setEnabled(false);
        cPhone.setText(currentCustomer.getCustomerPhone());
        cPhone.setFocusable(false);
        cPhone.setTextColor(ContextCompat.getColor(this, R.color.clickable_text));
        cPhone.setClickable(true);
        cEmail.setText(currentCustomer.getCustomerEmail());
        cEmail.setFocusable(false);
        cEmail.setTextColor(ContextCompat.getColor(this, R.color.clickable_text));
        cEmail.setClickable(true);

        //save current data
        currentName = cName.getText().toString();
        currentPhone = cPhone.getText().toString().trim();
        currentEmail = cEmail.getText().toString().trim();

        //set click listener for calling phone number
        cPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber();
            }
        });

        //set click listener to send email
        cEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_task:
                editSaveEmployee();
                return true;
//            case R.id.save_edits:
//                saveTask();
//                return true;
            case android.R.id.home:
                cancelScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void editSaveEmployee() {
        if (!editable) {
            //change edit icon to save
            menu.getItem(0).setIcon(R.drawable.ic_save_white);
            //enable the edit text fields
            cName.setEnabled(true);
            cPhone.setTextColor(originalTextColor);
            cPhone.setFocusableInTouchMode(true);
            cPhone.setFocusable(true);
            cPhone.setClickable(false);
            cEmail.setTextColor(originalTextColor);
            cEmail.setFocusableInTouchMode(true);
            cEmail.setFocusable(true);
            cEmail.setClickable(false);
            editable = true;
        }
        else {
            if (!cName.getText().toString().equals(currentName)
                    || !cPhone.getText().toString().equals(currentPhone)
                    || !cEmail.getText().toString().equals(currentEmail))
            {
                currentCustomer.setCustomerName(cName.getText().toString());
                currentCustomer.setCustomerPhone(cPhone.getText().toString());
                currentCustomer.setCustomerEmail(cEmail.getText().toString());

                mmvm.update(currentCustomer);
                closeActivity(RESULT_OK);
            }
            else {
                cancelScreen();
            }
        }
    }

    private void cancelScreen() {
        closeActivity(RESULT_CANCELED);
    }

    private void closeActivity(int resultCode) {
        Intent intent = new Intent();
        if (resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED, intent);
        }
        else  {
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    private void callPhoneNumber() {
        String phoneNumber = cPhone.getText().toString();
        if (phoneNumber.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(CustomerDetails.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CustomerDetails.this,
                        new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
            else {
                String dialNumber = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dialNumber)));
            }
        }
        else {
            Toast.makeText(this, "No Number To Call", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
            else {
                Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendEmail() {
        String emailRecipient = cEmail.getText().toString();
        if (emailRecipient.trim().length() > 0){
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, emailRecipient);
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Choose an email client"));
        }
    }
}
