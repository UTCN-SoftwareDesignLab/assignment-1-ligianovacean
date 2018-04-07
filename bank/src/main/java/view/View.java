package view;

import javax.swing.*;

public abstract class View extends JFrame{

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(getContentPane(), message);
    }

}
