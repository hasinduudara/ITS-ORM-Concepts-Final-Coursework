package lk.ijse.gdse.mentalhealth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapist")

public class Therapist {
    @Id
    @Column(name = "therapist_id", nullable = false, length = 50)
    private String therapistID;

    @Column(nullable = false)
    private String therapistName;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String availability;

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL)
    private List<TherapistProgram> therapistPrograms;

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;
}
