package com.pontallink_server.pontallink.dtos;

public record NotificationDTO(String status, Long userId) {

    public NotificationDTO(String status, Long userId) {
        this.status = status;
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }
}
