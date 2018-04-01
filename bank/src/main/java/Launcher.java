import controller.TableProcessing;

public class Launcher {


    public static void main(String[] args) {
        //ComponentFactory componentFactory = ComponentFactory.instance(false);
        TableProcessing tableProcessing = TableProcessing.instance();
        ControllerFactory controllerFactory = ControllerFactory.instance(false, tableProcessing);
    }

}
