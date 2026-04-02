package org.yash.quize_app.dto;

import lombok.Data;

import java.util.Map;

@Data
public class SubmitAttemptRequest {
    private Map<Long, String> selectedOptions;
}
