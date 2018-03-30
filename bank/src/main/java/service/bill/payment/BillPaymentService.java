package service.bill.payment;

import model.Bill;
import model.validation.Notification;

public interface BillPaymentService {

    Notification<Boolean> payBill(Bill bill, Long accountId, Double sum);

}
