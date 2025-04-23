package lk.ijse.gdse.mentalhealth.dto;

import lk.ijse.gdse.mentalhealth.entity.Patient;
import lk.ijse.gdse.mentalhealth.entity.TherapySession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentDTO {
    private String paymentId;
    private double amount;
    private String paymentDate;
    private String status;
    private String patientId;
    private String sessionId;

//    public PaymentDTO(String paymentId, double amount, String paymentDate, String status, Patient patient, TherapySession session) {
//        this.paymentId = paymentId;
//        this.amount = amount;
//        this.paymentDate = paymentDate;
//        this.status = status;
//        this.patientId = patient.getId();
//        this.sessionId = session.getSessionId();
//    }
}
