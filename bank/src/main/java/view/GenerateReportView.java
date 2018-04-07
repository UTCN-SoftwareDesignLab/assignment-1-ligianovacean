package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.text.View.Y_AXIS;

public class GenerateReportView extends View {

    private JLabel lblEmployee;
    private JTextField tfEmployee;
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JTextField tfStartDate;
    private JTextField tfEndDate;
    private JButton btnConfirm;
    private JTable tblReport;
    private JScrollPane scrollPane;

    public GenerateReportView() {
        this.setTitle("Generate Report Page");
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
        add(scrollPane);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblEmployee = new JLabel("Employee username");
        tfEmployee = new JTextField();
        lblStartDate = new JLabel("Start date(E.g.: March 3, 2017)");
        lblEndDate = new JLabel("End date(E.g.: March 3, 2017");
        tfStartDate = new JTextField();
        tfEndDate = new JTextField();
        btnConfirm = new JButton("Generate Report)");
        tblReport = new JTable();
        scrollPane = new JScrollPane(tblReport);
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

    public void loadTable(JTable tbl) {
        this.tblReport = tbl;
        scrollPane.setViewportView(tblReport);
        revalidate();
        repaint();
    }
}
