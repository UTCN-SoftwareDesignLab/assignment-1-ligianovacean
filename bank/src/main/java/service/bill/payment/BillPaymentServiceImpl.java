package service.bill.payment;

import model.Account;
import model.Bill;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.bill.BillRepository;

public class BillPaymentServiceImpl implements BillPaymentService{

    private final AccountRepository accountRepository;
    private final BillRepository billRepository;

    public BillPaymentServiceImpl(AccountRepository accountRepository, BillRepository billRepository) {
        this.accountRepository = accountRepository;
        this.billRepository = billRepository;
    }


    @Override
    public Notification<Boolean> payBill(Bill bill, Long accountId, Double sum) {
        Notification<Boolean> paymentNotification = new Notification<>();
        try {
            Account account = accountRepository.findById(accountId).getResult();
            Double balance = account.getSum();
            boolean balanceChecked = checkBalance(balance, sum);
            if (balanceChecked == false) {
                paymentNotification.addError("Not enough money on your account!");
                paymentNotification.setResult(Boolean.FALSE);
            } else {
                boolean saveBill = billRepository.save(bill);
                if (saveBill) {
                    paymentNotification.setResult(Boolean.TRUE);
                    account.setSum(balance - sum);
                    accountRepository.update(account);
                } else {
                    paymentNotification.addError("Bill already paid!");
                    paymentNotification.setResult(Boolean.FALSE);
                }
            }
        } catch (EntityNotFoundException exc) {
            paymentNotification.addError("Misteriously, the account could not be found!");
        }
        return paymentNotification;
    }

    private boolean checkBalance(Double balance, Double sum) {
        if (balance < sum) {
            return false;
        }
        return true;
    }
}
