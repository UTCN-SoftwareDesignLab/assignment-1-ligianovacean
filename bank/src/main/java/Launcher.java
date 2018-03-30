import controller.AddClientController;
import controller.EmployeeController;
import controller.LoginController;
import controller.TableProcessing;
import database.Constants;
import model.Client;
import model.builder.ClientBuilder;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Launcher {


    public static void main(String[] args) {
        //ComponentFactory componentFactory = ComponentFactory.instance(false);
        TableProcessing tableProcessing = TableProcessing.instance();
        ControllerFactory controllerFactory = ControllerFactory.instance(false, tableProcessing);
    }

}
