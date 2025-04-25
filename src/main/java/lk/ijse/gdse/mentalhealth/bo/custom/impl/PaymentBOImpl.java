package lk.ijse.gdse.mentalhealth.bo.custom.impl;

import lk.ijse.gdse.mentalhealth.bo.custom.PaymentBO;
import lk.ijse.gdse.mentalhealth.dao.custom.PatientDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.PaymentDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapySessionDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.PatientDAOImpl;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.PaymetDAOImpl;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.gdse.mentalhealth.dto.PaymentDTO;
import lk.ijse.gdse.mentalhealth.entity.Patient;
import lk.ijse.gdse.mentalhealth.entity.Payment;
import lk.ijse.gdse.mentalhealth.entity.TherapySession;

import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    private final PaymentDAO paymentDAO = new PaymetDAOImpl();
    private final TherapySessionDAO therapySessionDAO = new TherapySessionDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) {
        Patient patient = patientDAO.findById(paymentDTO.getPatientId());
        TherapySession session = therapySessionDAO.findById(paymentDTO.getSessionId());

        if (patient == null || session == null) {
            return false;
        }

        Payment payment = new Payment(
                paymentDTO.getPaymentId(),
                paymentDTO.getAmount(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getStatus(),
                patient,
                session
        );

        return paymentDAO.save(payment); // Save the entity, not the DTO
    }


    @Override
    public String getNextPaymentID() {
        return paymentDAO.getNextId();
    }

    @Override
    public ArrayList<PaymentDTO> loadAllPayments() {
        ArrayList<PaymentDTO> paymentDTOList = new ArrayList<>();
        try {
            List<Payment> paymentList = paymentDAO.getAll();

            for (Payment payment : paymentList) {
                String sessionId = (payment.getSession() != null) ? payment.getSession().getSessionId() : "N/A";

                PaymentDTO dto = new PaymentDTO(
                        payment.getPaymentId(),
                        payment.getAmount(),
                        payment.getPaymentDate().toString(),
                        payment.getStatus(),
                        payment.getPatient().getId(),
                        sessionId
                );
                paymentDTOList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentDTOList;
    }

    @Override
    public long getCompletedPaymentCount() {
        try {
            return paymentDAO.getAll().stream()
                    .filter(payment -> "completed".equalsIgnoreCase(payment.getStatus()))
                    .count();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getPendingPaymentCount() {
        try {
            return paymentDAO.getAll().stream()
                    .filter(payment -> "pending".equalsIgnoreCase(payment.getStatus()))
                    .count();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
