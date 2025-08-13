package upd.dev.authskeleton.dto;

import lombok.Data;

@Data
public class SessionStatus {
    private boolean active;
    private String username;

    public SessionStatus(boolean active, String username) {
        this.active = active;
        this.username = username;
    }
}