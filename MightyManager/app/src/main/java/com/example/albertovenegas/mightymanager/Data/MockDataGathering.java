package com.example.albertovenegas.mightymanager.Data;

import java.util.ArrayList;
import java.util.List;

public class MockDataGathering {
    private static final String[] assignmentTitles = {"Assignment 1", "Assignment 2", "Assignment 3", "Assignment 4"};
    private static final String[] employeeNames = {"Alberto", "Kris", "Jeff", "Travis", "Phillip"};
    private static final int[] icons = {android.R.drawable.editbox_background, android.R.drawable.ic_menu_edit};

    public static List<Assignment> getAssignmentData()
    {
        List<Assignment> assignmentData = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < assignmentTitles.length; i++) {
                Assignment newAssignment = new Assignment();
                newAssignment.setTitle(assignmentTitles[i]);
                newAssignment.setAssignedEmployee(employeeNames[i]);
                newAssignment.setStatusIcon(icons[0]);
                newAssignment.setEditIcon(icons[1]);
                assignmentData.add(newAssignment);
            }
        }

        return assignmentData;
    }
}
