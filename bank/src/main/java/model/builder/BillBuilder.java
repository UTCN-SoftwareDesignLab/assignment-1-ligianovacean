package model.builder;

import model.Bill;

public class BillBuilder {

    private Bill bill;

    public BillBuilder() {
        this.bill = new Bill();
    }

    public BillBuilder setId(Long id) {
        bill.setId(id);
        return this;
    }

    public BillBuilder setIdentifier(String identifier) {
        bill.setIdentif(identifier);
        return this;
    }

    public BillBuilder setSum(Double sum) {
        bill.setSum(sum);
        return this;
    }

    public BillBuilder setIdClient(Long id) {
        bill.setId_client(id);
        return this;
    }

    public Bill build(){
        return this.bill;
    }
}
