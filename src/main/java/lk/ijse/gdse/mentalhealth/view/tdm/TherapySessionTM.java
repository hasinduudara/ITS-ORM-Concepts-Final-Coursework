package lk.ijse.gdse.mentalhealth.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapySessionTM {
    private String sessionId;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String status;
    private String patientId;
    private String programId;
    private String therapistId;
}
