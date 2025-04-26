package lk.ijse.gdse.mentalhealth.dao.custom.impl;

import lk.ijse.gdse.mentalhealth.config.FactoryConfiguration;
import lk.ijse.gdse.mentalhealth.dao.custom.PaymentDAO;
import lk.ijse.gdse.mentalhealth.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PaymetDAOImpl implements PaymentDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

//    @Override
//    public boolean save(Payment entity) {
//        Session session = factoryConfiguration.getSession();
//        Transaction transaction = session.beginTransaction();
//
//        try {
//            Payment existingPayment = session.get(Payment.class, entity.getPaymentId());
//            if (existingPayment != null) {
//                throw new RuntimeException("Payment ID already exists");
//            }
//            session.persist(entity);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            transaction.rollback();
//            throw new RuntimeException(e);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }

    @Override
    public boolean save(Payment entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Payment existingPayment = session.get(Payment.class, entity.getPaymentId());
            if (existingPayment != null) {
                throw new RuntimeException("Payment ID already exists");
            }
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Payment entity) {
        return false;
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        return false;
    }

    @Override
    public List<Payment> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<Payment> paymentList = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            paymentList = session.createQuery("FROM Payment", Payment.class).list();
            transaction.commit();
            return paymentList;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
                e.printStackTrace();
        } finally {
            session.close();
        }
        return paymentList;
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
            return String.format("PA00-%03d", numericPart);
        } else {
            return "PA00-001"; // Default ID if no records exist
        }
    }

    @Override
    public Payment findById(String id) {
        return null;
    }
}
