package project.clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    private String time;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    private String reason;

    @OneToOne
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "info")
    private Patient patient;

}
