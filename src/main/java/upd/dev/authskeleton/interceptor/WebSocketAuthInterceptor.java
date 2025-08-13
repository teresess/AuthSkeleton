package upd.dev.authskeleton.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            Principal user = accessor.getUser();
            if (user == null && requiresAuthentication(accessor)) {
                throw new SecurityException("Not authenticated");
            }
        }

        return message;
    }

    private boolean requiresAuthentication(StompHeaderAccessor accessor) {
        return !"CONNECT".equals(accessor.getCommand().name()) &&
                !"DISCONNECT".equals(accessor.getCommand().name());
    }
}