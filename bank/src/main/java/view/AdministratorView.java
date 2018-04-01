package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.text.View.Y_AXIS;

public class AdministratorView extends JFrame{

    private JRadioButton rbCreateEmployee;
    private JRadioButton rbViewEmployee;
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
        add(rbGenerateReport);
        add(btnConfirmSelection);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        rbCreateEmployee = new JRadioButton("Create Employee");
        rbViewEmployee = new JRadioButton("RUD Employee");
        rbGenerateReport = new JRadioButton("Generate Report");
        btnGroup = new ButtonGroup();
        btnGroup.add(rbCreateEmployee);
        btnGroup.add(rbViewEmployee);
        btnGroup.add(rbGenerateReport);
        btnConfirmSelection = new JButton("Confirm Selection");
    }

    public String getSelection() {
        if (rbCreateEmployee.isSelected())
            return "CreateEmployee";
        if (rbViewEmployee.isSelected())
            return "RUDEmployee";
        if (rbGenerateReport.isSelected())
            return "GenerateReport";
        return "";
    }

    public void setConfirmActionListener(ActionListener btnActionListener) {
        btnConfirmSelection.addActionListener(btnActionListener);
    }

    public void setVisibility(Boolean bool) {
        if (bool) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

}
