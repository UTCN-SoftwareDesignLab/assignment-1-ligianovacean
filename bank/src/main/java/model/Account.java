package model;

import java.util.Date;

public class Account {

    private Long id;
    private String type;
    private Double sum;
    private Date creation_date;
    private Long id_client;

//    public Account(String type, Double sum, Date creation_date, Long id_client) {
//        this.type = type;
//        this.sum = sum;
//        this.creation_date = creation_date;
//        this.id_client = id_client;
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }
}
