package com.example.albertovenegas.mightymanager.UserInterface;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;

public class UserNameGenerator {
    private String username;
    private MightyManagerViewModel mmvm;

    public UserNameGenerator(Employee employee)
    {
        this.username = generatedUsername(employee.getEmployeeFirstName().toLowerCase(), employee.getEmployeeLastName().toLowerCase());

    }

    private boolean nameExists(String username) {
        if(mmvm.findEmployeeByUsername(username) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    private String generatedUsername(String firstName, String lastName) {
        String candidateName = "";
        candidateName = candidateName.concat(firstName.charAt(0) + lastName);
        int i = 1;
        while(nameExists(candidateName) && i < firstName.length())
        {
            candidateName = "";
            for (int j = 0; j <= i; j++)
            {
                candidateName = candidateName.concat(firstName.charAt(j) + "");
            }
            candidateName = candidateName.concat(lastName);
        }
        return candidateName;
    }

    public String getUsername() {
        return this.username;
    }
}
