package lk.ijse.gdse.mentalhealth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment")

public class Payment {
    @Id
    private String paymentId;
    private double amount;
    private LocalDate paymentDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "session_id")
    private TherapySession session;

    public Payment(String paymentId, double amount, String paymentDate, String status, Patient patient, TherapySession session) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = LocalDate.parse(paymentDate);
        this.status = status;
        this.patient = patient;
        this.session = session;
    }
}
