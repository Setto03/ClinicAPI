package project.clinic.controller.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionRequest {

    private String time;

    private int price;

    private Long doctor;

    private String reason;
}
