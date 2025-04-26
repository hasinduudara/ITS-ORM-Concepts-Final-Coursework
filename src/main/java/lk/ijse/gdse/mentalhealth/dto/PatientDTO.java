package lk.ijse.gdse.mentalhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PatientDTO {
    private String id;
    private String name;
    private String phoneNumber;
    private String gender;
    private String medicalHistory;
}
