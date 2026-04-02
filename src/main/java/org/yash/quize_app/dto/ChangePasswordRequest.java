package org.yash.quize_app.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
