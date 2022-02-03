package com.beamedcallum.database.accounts.models;

import java.time.Instant;

public class Account {
    private String username;
    private String password;
    private Instant createdAt;
    private String roleCSV = "ROLE_USER";
    private boolean enabled = true;
    private boolean passwordExpired = false;

    protected Account() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public static class AccountBuilder{
        private boolean buildActive = true;
        private Account account = new Account();

        public void setUsername(String username){
            if (buildActive){
                account.username = username;
            }
        }

        public void setPassword(String password){
            if (buildActive){
                account.password = password;
            }
        }

        public void setCreatedAt(Instant createdAt){
            if (buildActive){
                account.createdAt = createdAt;
            }
        }

        public void setRoleCSV(String roleCSV){
            if (buildActive){
                account.roleCSV = roleCSV;
            }
        }

        public void setEnabled(boolean enabled){
            if (buildActive){
                account.enabled = enabled;
            }
        }

        public void setPasswordExpired(boolean passwordExpired){
            if (buildActive){
                account.passwordExpired = passwordExpired;
            }
        }

        public Account build(){
            buildActive = false;
            return account;
        }
    }
}
