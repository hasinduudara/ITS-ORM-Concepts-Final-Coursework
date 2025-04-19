package lk.ijse.gdse.mentalhealth.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapistTM {
    private String therapistID;
    private String therapistName;
    private String specialization;
    private String availability;
}
