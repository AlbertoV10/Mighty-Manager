package com.example.albertovenegas.mightymanager.Data;

import java.util.ArrayList;
import java.util.List;

public class MockDataGathering {
    private static final String[] assignmentTitles = {"Assignment 1", "Assignment 2", "Assignment 3", "Assignment 4"};
    private static final String[] employeeNames = {"Alberto", "Kris", "Jeff", "Travis", "Phillip"};

    public static List<Assignment> getAssignmentData()
    {
        List<Assignment> assignmentData = new ArrayList<>();

        for(int i = 0; i < assignmentTitles.length; i++)
        {
            Assignment newAssignment = new Assignment();
            newAssignment.setTitle(assignmentTitles[i]);
            newAssignment.setAssignedEmployee(employeeNames[i]);
            newAssignment.setStatus(0);
            assignmentData.add(newAssignment);
        }

        return assignmentData;
    }
}
