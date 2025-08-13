package upd.dev.authskeleton.service;

import upd.dev.authskeleton.model.User;
import upd.dev.authskeleton.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public UserService(UserRepository userRepository,
                       RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public boolean register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public List<User> all() {
        return userRepository.findAll();
    }

    public boolean login(User user, HttpSession session) {
        return userRepository.findByUsername(user.getUsername())
                .map(dbUser -> {
                    if (user.getPassword().equals(dbUser.getPassword())) {
                        session.setAttribute("user", dbUser.getUsername());
                        return true;
                    }
                    return false;
                })
                .orElse(false);
    }

    public boolean checkSession(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public void logout(HttpSession session) {
        redisTemplate.delete("user:session:" + session.getId());
        session.invalidate();
    }
}