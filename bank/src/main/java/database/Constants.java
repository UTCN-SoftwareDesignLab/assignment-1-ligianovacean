package database;

import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String BILL = "bill";
        public static final String ACTIVITY = "activity";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, CLIENT, ACCOUNT, BILL, ACTIVITY};
    }


    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }


    public static class Rights {
        public static final String CREATE_USER = "Create user";
        public static final String VIEW_USER = "View user";
        public static final String UPDATE_USER = "Update user";
        public static final String DELETE_USER = "Delete user";

        public static final String CREATE_ACCOUNT = "Create account";
        public static final String DELETE_ACCOUNT = "Delete account";
        public static final String UPDATE_ACCOUNT = "Update account";
        public static final String VIEW_ACCOUNT = "View account";

        public static final String TRANSFER_MONEY = "Transfer money";
        public static final String PROCESS_UTILITY_BILLS = "Process utility bills";
        public static final String GENERATE_REPORT = "Generate report";

        public static final String CREATE_CLIENT = "Create client";
        public static final String VIEW_CLIENT = "View client";
        public static final String DELETE_CLIENT = "Delete client";
        public static final String UPDATE_CLIENT = "Update client";

        public static final String RUD_CLIENT = "RUD Client";
        public static final String RUD_USER = "RUD User";
        public static final String RUD_ACCOUNT = "RUD Account";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, VIEW_USER, CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, VIEW_ACCOUNT, TRANSFER_MONEY, PROCESS_UTILITY_BILLS, GENERATE_REPORT, CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, RUD_ACCOUNT, RUD_CLIENT, RUD_USER};
    }



    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList( CREATE_USER, RUD_USER, GENERATE_REPORT));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENT, RUD_CLIENT, CREATE_ACCOUNT, RUD_ACCOUNT, TRANSFER_MONEY, PROCESS_UTILITY_BILLS));

        return ROLES_RIGHTS;
    }

    public static class Columns {
        public static final String[] CLIENT_TABLE_COLUMNS = new String[] {"id", "name", "Id Card No.", "PNC", "Address"};
        public static final String[] ACCOUNT_TABLE_COLUMNS = new String[] {"id", "type", "sum", "creation_date", "id_client"};
        public static final String[] EMPLOYEE_TABLE_COLUMNS = new String[] {"id", "username"};
        public static final String[] ACTIVITY_TABLE_COLUMNS = new String[] {"id", "user", "user_role", "date", "description"};
    }

}
