package view;

import database.Constants;
import model.Right;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;
import static javax.swing.text.View.Y_AXIS;

public class EmployeeView extends View {

    private List<JRadioButton> rb;
    private ButtonGroup btnGroup;
    private JButton btnConfirmSelection;

    public EmployeeView() throws HeadlessException {
        this.setTitle("Employee's First Page");
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        for (JRadioButton jRadioButton : rb) {
            add(jRadioButton);
        }
        add(btnConfirmSelection);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        rb = new ArrayList<>();
        List<String> rights = Constants.getRolesRights().get(EMPLOYEE);
        for (String right : rights) {
            rb.add(new JRadioButton(right));
        }
        btnGroup = new ButtonGroup();
        for (JRadioButton jRadioButton : rb) {
            btnGroup.add(jRadioButton);
        }
        btnConfirmSelection = new JButton("Confirm Selection");
    }

    public String getSelection() {
       for (JRadioButton rb : rb) {
           if (rb.isSelected())
               return rb.getText();
       }
       return "";
    }

    public void setConfirmSelectionButtonListener(ActionListener confirmSelectionButtonListener) {
        btnConfirmSelection.addActionListener(confirmSelectionButtonListener);
    }


}
