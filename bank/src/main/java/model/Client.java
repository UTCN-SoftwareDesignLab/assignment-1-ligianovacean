package model;

import java.util.List;

public class Client {

    private Long id;
    private String name;
    private String id_card_no;
    private String personal_numerical_code;
    private String address;
    private List<Account> accounts;

//    public Client(String name, String id_card_no, String personal_numerical_code, String address) {
//        this.name = name;
//        this.id_card_no = id_card_no;
//        this.personal_numerical_code = personal_numerical_code;
//        this.address = address;
//    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card_no() {
        return id_card_no;
    }

    public void setId_card_no(String id_card_no) {
        this.id_card_no = id_card_no;
    }

    public String getPersonal_numerical_code() {
        return personal_numerical_code;
    }

    public void setPersonal_numerical_code(String personal_numerical_code) {
        this.personal_numerical_code = personal_numerical_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
