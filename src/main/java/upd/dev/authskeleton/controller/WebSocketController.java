package upd.dev.authskeleton.controller;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final Set<String> activeUsers = ConcurrentHashMap.newKeySet();

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/user.connect")
    public void addUser(Principal principal) {
        activeUsers.add(principal.getName());
        broadcastUsers();
    }

    @MessageMapping("/user.disconnect")
    public void removeUser(Principal principal) {
        activeUsers.remove(principal.getName());
        broadcastUsers();
    }

    private void broadcastUsers() {
        messagingTemplate.convertAndSend("/topic/activeUsers", new ArrayList<>(activeUsers));
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String username = event.getUser().getName();
        activeUsers.remove(username);
        broadcastUsers();
    }
}