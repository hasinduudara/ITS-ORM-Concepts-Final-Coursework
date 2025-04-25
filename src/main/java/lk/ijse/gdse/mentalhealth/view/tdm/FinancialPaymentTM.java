package lk.ijse.gdse.mentalhealth.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class FinancialPaymentTM extends PaymentTM {
    private String paymentId;
    private double amount;
    private String status;
}
