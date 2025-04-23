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
@Table(name = "patient")

public class Patient {
    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String gender;
    private String medicalHistory;

    public <E> Patient(String id, String name, String phoneNumber, String gender, String medicalHistory, ArrayList<E> es, ArrayList<E> es1) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.medicalHistory = medicalHistory;
    }

    @OneToMany(mappedBy = "patient" , cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;
//
//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
//    private List<PaymentTM> payments;
}
