package repository.bill;

import model.Bill;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface BillRepository {

    boolean save(Bill bill);

    boolean findByIdentifier(String identifier);

    public void removeAll();

}
