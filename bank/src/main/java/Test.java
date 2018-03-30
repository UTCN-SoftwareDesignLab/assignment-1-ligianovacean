import view.CreateAccountView;
import view.ProcessUtilityBillsView;
import view.TransferView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {

    private static String[] columnNames = {"A", "B", "C"};
    private static Object[][] data = {
            {"Moni", "adsad", 2},
            {"Jhon", "ewrewr", 4},
            {"Max", "zxczxc", 6}
    };
    private static CreateAccountView test;

    public static void main(String[] args) {
        //new LoginController(new LoginView(), componentFactory.getAuthenticationService());
        //new EmployeeController(new EmployeeView());
        //new AddClientView();

//        String[] columnNames = {"A", "B", "C"};
//        Object[][] data = {
//                {"Moni", "adsad", 2},
//                {"Jhon", "ewrewr", 4},
//                {"Max", "zxczxc", 6}
//        };
        //new RUDView(columnNames, data);

        //new TransferView();
        new ProcessUtilityBillsView();
        //CreateAccountView test = new CreateAccountView(columnNames, data);

//        test = new CreateAccountView(columnNames, data);
//        test.setCreateButtonActionListener(new TestTable());

        //new CreateEmployeeView();

        //new GenerateReportView();
//
//        Client client1 = new ClientBuilder().setId(new Long(1)).setAddress("ciuciu").setPersonal_numerical_code("1234567891234").setId_card_no("1254").setName("eu tu").build();
//        Client client2 = new ClientBuilder().setId(new Long(2)).setAddress("aha").setPersonal_numerical_code("5454567891234").setId_card_no("1254").setName("na na").build();
//
//        List<Client> lala = new ArrayList<>();
//        lala.add(client1);
//        lala.add(client2);
//
//        JTable test = tableProcessing.generateTable(lala, Constants.Columns.CLIENT_TABLE_COLUMNS);
//        System.out.println(test.getValueAt(1, 2));
        //RUDView view = new RUDView();
        //view.loadTable(test);
    }


    private static class TestTable implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(test.getClientId());
        }
    }


}
