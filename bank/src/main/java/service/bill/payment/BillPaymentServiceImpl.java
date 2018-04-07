package service.bill.payment;

import model.Account;
import model.Bill;
import model.validation.Notification;
import model.validation.PaymentValidator;
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
        PaymentValidator paymentValidator;
        Notification<Boolean> paymentNotification = new Notification<>();
        try {
            Account account = accountRepository.findById(accountId).getResult();
            paymentValidator = new PaymentValidator(bill, account);
            boolean isValid = paymentValidator.validate();

            if (!isValid) {
                paymentValidator.getErrors().forEach(paymentNotification::addError);
                paymentNotification.setResult(Boolean.FALSE);
            } else {
                boolean saveBill = billRepository.save(bill);
                if (saveBill) {
                    paymentNotification.setResult(Boolean.TRUE);
                    account.setSum(account.getSum() - sum);
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

    @Override
    public void removeAll() {
        billRepository.removeAll();
    }

}
