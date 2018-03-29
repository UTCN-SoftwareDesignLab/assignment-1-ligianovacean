package view;

import javax.swing.*;

import static javax.swing.text.View.Y_AXIS;

public class AdministratorView extends JFrame{

    private JRadioButton rbCreateEmployee;
    private JRadioButton rbViewEmployee;
    private JRadioButton rbUpdateEmployee;
    private JRadioButton rbDeleteEmployee;
    private JRadioButton rbGenerateReport;
    private ButtonGroup btnGroup;
    private JButton btnConfirmSelection;

    public AdministratorView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(rbCreateEmployee);
        add(rbViewEmployee);
        add(rbUpdateEmployee);
        add(rbDeleteEmployee);
        add(rbGenerateReport);
        add(btnConfirmSelection);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        rbCreateEmployee = new JRadioButton("Create Employee");
        rbViewEmployee = new JRadioButton("View Employee");
        rbUpdateEmployee = new JRadioButton("Update Employee");
        rbDeleteEmployee = new JRadioButton("Delete Employee");
        rbGenerateReport = new JRadioButton("Generate Report");
        btnGroup = new ButtonGroup();
        btnGroup.add(rbCreateEmployee);
        btnGroup.add(rbViewEmployee);
        btnGroup.add(rbUpdateEmployee);
        btnGroup.add(rbDeleteEmployee);
        btnGroup.add(rbGenerateReport);
        btnConfirmSelection = new JButton("Confirm Selection");
    }

    public String getSelection() {
        if (rbCreateEmployee.isSelected())
            return "Create Employee";
        if (rbViewEmployee.isSelected())
            return "View Employee";
        if (rbUpdateEmployee.isSelected())
            return "Update Employee";
        if (rbDeleteEmployee.isSelected())
            return "Delete Employee";
        if (rbGenerateReport.isSelected())
            return "Generate Report";
        return "";
    }

}
