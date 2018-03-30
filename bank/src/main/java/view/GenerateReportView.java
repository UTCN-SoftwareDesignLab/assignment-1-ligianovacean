package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.text.View.Y_AXIS;

public class GenerateReportView extends JFrame {

    private JLabel lblEmployee;
    private JTextField tfEmployee;
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JTextField tfStartDate;
    private JTextField tfEndDate;
    private JButton btnConfirm;
    private JButton btnOpenReport;

    public GenerateReportView() {
        setSize(320, 280);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblEmployee);
        add(tfEmployee);
        add(lblStartDate);
        add(tfStartDate);
        add(lblEndDate);
        add(tfEndDate);
        add(btnConfirm);
        add(btnOpenReport);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblEmployee = new JLabel("Employee identifier");
        tfEmployee = new JTextField();
        lblStartDate = new JLabel("Start date");
        lblEndDate = new JLabel("End date");
        tfStartDate = new JTextField();
        tfEndDate = new JTextField();
        btnConfirm = new JButton("Generate Report");
        btnOpenReport = new JButton("View Report");
    }

    public String getUsername() {
        return tfEmployee.getText();
    }

    public String getStartDate() {
        return tfStartDate.getText();
    }

    public String getEndDate() {
        return tfEndDate.getText();
    }

    public void setGenerateButtonActionListener(ActionListener btnGenerateButtonListener) {
        btnConfirm.addActionListener(btnGenerateButtonListener);
    }

    public void setViewReportButtonActionListener(ActionListener btnViewReportListener) {
        btnOpenReport.addActionListener(btnViewReportListener);
    }

    public void setVisibility(Boolean bool) {
        if (bool) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }
}
