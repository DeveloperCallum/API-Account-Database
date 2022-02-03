package com.beamedcallum.database.accounts.models;

import java.time.Instant;

public class AccountInfo {
    private String username;
    private Instant createdAt;
    private String roleCSV;
    private boolean enabled;
    private boolean passwordExpired;

    public AccountInfo(Account account) {
        this.username = account.getUsername();
        this.createdAt = account.getCreatedAt();
        this.roleCSV = account.getRoleCSV();
        this.enabled = account.isEnabled();
        this.passwordExpired = account.isPasswordExpired();
    }

    public String getUsername() {
        return username;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getRoleCSV() {
        return roleCSV;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }
}
