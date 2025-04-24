package lk.ijse.gdse.mentalhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapySessionDTO {
    private String sessionId;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    //    private String sessionDate;
//    private String sessionTime;
    private String status;
    private String patientId;
    private String programId;
    private String therapistId;

    public TherapySessionDTO(String sessionId, String id, String therapistID, String programId, LocalDate parse, LocalTime parse1, String status) {
    }

    public TherapySessionDTO(String sessionId, String id, String therapistID, String programId, String sessionDate, String sessionTime, String status) {

    }

}

