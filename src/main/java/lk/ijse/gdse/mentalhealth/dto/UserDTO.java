package lk.ijse.gdse.mentalhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor@Data

public class UserDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
}
