package lk.ijse.gdse.mentalhealth.dao.custom;

import lk.ijse.gdse.mentalhealth.entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment> {
    Payment findById(String id);
}
