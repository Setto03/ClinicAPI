package project.clinic.controller.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.clinic.model.Session;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponse {

    private String message;

    private Session session;

}
