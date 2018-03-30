package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        switch(table) {
            case USER:
                return "CREATE TABLE IF NOT EXISTS user (" +
                        " id INT NOT NULL AUTO_INCREMENT," +
                        " username VARCHAR(200) NOT NULL," +
                        " password VARCHAR(64) NOT NULL," +
                        " PRIMARY KEY (id)," +
                        " UNIQUE INDEX id_UNIQUE (id ASC)," +
                        " UNIQUE INDEX username_UNIQUE (username ASC));";
            case ROLE:
                return "CREATE TABLE IF NOT EXISTS role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX role_UNIQUE (role ASC));";
            case RIGHT:
                return "CREATE TABLE IF NOT EXISTS `right` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `right` VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                        "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";
            case ROLE_RIGHT:
                return "CREATE TABLE IF NOT EXISTS role_right (" +
                        " id INT NOT NULL AUTO_INCREMENT," +
                        " id_role INT NOT NULL," +
                        " id_right INT NOT NULL," +
                        " PRIMARY KEY (id)," +
                        " UNIQUE INDEX id_UNIQUE (id ASC)," +
                        " INDEX id_role_idx (id_role ASC)," +
                        " INDEX id_right_idx (id_right ASC)," +
                        " CONSTRAINT id_role" +
                        "   FOREIGN KEY (id_role)" +
                        "   REFERENCES role (id)" +
                        "   ON DELETE CASCADE" +
                        "   ON UPDATE CASCADE," +
                        " CONSTRAINT id_right" +
                        "   FOREIGN KEY (id_right)" +
                        "   REFERENCES `right` (id)" +
                        "   ON DELETE CASCADE" +
                        "   ON UPDATE CASCADE);";
            case USER_ROLE:
                return "CREATE TABLE IF NOT EXISTS user_role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  id_user INT NOT NULL," +
                        "  id_role INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX id_user_idx (id_user ASC)," +
                        "  INDEX id_role_idx (id_role ASC)," +
                        "  CONSTRAINT user_fkid" +
                        "    FOREIGN KEY (id_user)" +
                        "    REFERENCES user (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT role_fkid" +
                        "    FOREIGN KEY (id_role)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";
            case CLIENT:
                return "CREATE TABLE IF NOT EXISTS client(" +
                        " id INT NOT NULL AUTO_INCREMENT," +
                        " name VARCHAR(200) NOT NULL," +
                        " id_card_no VARCHAR(10) NOT NULL," +
                        " personal_numerical_code VARCHAR(13) NOT NULL," +
                        " address VARCHAR(100) NOT NULL," +
                        " PRIMARY KEY (id)," +
                        " UNIQUE INDEX id_UNIQUE (id ASC)," +
                        " UNIQUE INDEX personal_numerical_code_UNIQUE (personal_numerical_code ASC));";
            case ACCOUNT:
                return "CREATE TABLE IF NOT EXISTS account(" +
                        " id int NOT NULL AUTO_INCREMENT," +
                        " type VARCHAR(20) NOT NULL," +
                        " sum DOUBLE NOT NULL," +
                        " creation_date DATE NOT NULL," +
                        " id_client INT NOT NULL," +
                        " PRIMARY KEY (id)," +
                        " UNIQUE INDEX id_UNIQUE (id ASC)," +
                        " INDEX id_client_idx (id_client ASC)," +
                        "  CONSTRAINT client_fkid" +
                        "    FOREIGN KEY (id_client)" +
                        "    REFERENCES client (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";
            case BILL:
                return "CREATE TABLE IF NOT EXISTS bill(" +
                       " id int NOT NULL AUTO_INCREMENT," +
                       " identif VARCHAR(20) NOT NULL," +
                       " sum DOUBLE NOT NULL," +
                       " id_client int NOT NULL," +
                       " PRIMARY KEY (id)," +
                       " UNIQUE INDEX id_UNIQUE (id ASC)," +
                       " UNIQUE INDEX identif_UNIQUE (identif ASC)," +
                       " INDEX id_client_idx (id_client ASC)," +
                       " CONSTRAINT id_fkclient" +
                       "    FOREIGN KEY (id_client)" +
                       "    REFERENCES client (id)" +
                       "    ON DELETE CASCADE" +
                       "    ON UPDATE CASCADE);";
            default:
                return "";
        }


    }

}
