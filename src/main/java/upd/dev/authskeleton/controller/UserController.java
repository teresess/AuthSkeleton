package upd.dev.authskeleton.controller;

import org.springframework.http.ResponseEntity;
import upd.dev.authskeleton.model.User;
import upd.dev.authskeleton.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody User user) {
        boolean result = userService.register(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody User user, HttpSession session) {
        boolean result = userService.login(user, session);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check-session")
    public ResponseEntity<Boolean> checkSession(HttpSession session) {
        boolean result = userService.checkSession(session);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        userService.logout(session);
        return ResponseEntity.ok().build();
    }
}