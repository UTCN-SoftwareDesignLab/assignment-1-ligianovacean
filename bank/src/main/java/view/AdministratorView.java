package view;

import database.Constants;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static javax.swing.text.View.Y_AXIS;

public class AdministratorView extends View {

    private List<JRadioButton> rb;
    private ButtonGroup btnGroup;
    private JButton btnConfirmSelection;

    public AdministratorView() {
        this.setTitle("Administrator's First Page");
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        for(JRadioButton jRadioButton : rb) {
            add(jRadioButton);
        }
        add(btnConfirmSelection);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        rb = new ArrayList<>();
        List<String> rights = Constants.getRolesRights().get(ADMINISTRATOR);
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
        for (JRadioButton jRadioButton : rb) {
            if (jRadioButton.isSelected()) {
                return jRadioButton.getText();
            }
        }
        return "";
    }

    public void setConfirmActionListener(ActionListener btnActionListener) {
        btnConfirmSelection.addActionListener(btnActionListener);
    }

}
