package lk.ijse.gdse.mentalhealth.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentTM {
    private String paymentId;
    private double amount;
    private String paymentDate;
    private String status;
    private String patientId;
    private String sessionId;

    public PaymentTM(String paymentId, double amount, String status) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
    }
}
