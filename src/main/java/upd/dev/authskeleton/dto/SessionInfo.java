package upd.dev.authskeleton.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SessionInfo {
    private String username;
    private Date creationTime;
    private Date lastAccessedTime;
    private int maxInactiveInterval;
    private boolean expired;

    public SessionInfo(String username, Date creationTime, Date lastAccessedTime,
                       int maxInactiveInterval, boolean expired) {
        this.username = username;
        this.creationTime = creationTime;
        this.lastAccessedTime = lastAccessedTime;
        this.maxInactiveInterval = maxInactiveInterval;
        this.expired = expired;
    }
}