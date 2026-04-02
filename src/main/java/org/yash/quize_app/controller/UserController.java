package org.yash.quize_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.yash.quize_app.dto.ChangePasswordRequest;
import org.yash.quize_app.service.AuthService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        authService.changePassword(email, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully");
    }
}
