package com.beamedcallum.database.accounts.actions.commands;

import com.beamedcallum.database.Database;
import com.beamedcallum.database.DatabaseCommand;
import com.beamedcallum.database.accounts.models.Account;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccountCommand extends DatabaseCommand {
    private final PasswordEncoder passwordEncoder;
    private final String username;
    private final String password;
    private final String roleCSV;
    private boolean enabled = true;

    public CreateAccountCommand(Database databaseService, PasswordEncoder passwordEncoder, String username, String encryptedPassword, String roleCSV) {
        super(databaseService);
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.password = encryptedPassword;
        this.roleCSV = roleCSV;
    }

    public CreateAccountCommand(Database databaseService, PasswordEncoder passwordEncoder, String username, String encryptedPassword, String roleCSV, boolean enabled) {
        super(databaseService);
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.password = encryptedPassword;
        this.roleCSV = roleCSV;
        this.enabled = enabled;
    }

    public CreateAccountCommand(Database databaseService, PasswordEncoder passwordEncoder, Account account) {
        super(databaseService);
        this.passwordEncoder = passwordEncoder;
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.roleCSV = account.getRoleCSV();
        this.enabled = account.isEnabled();
    }

    protected void execute() throws SQLException {
        try (PreparedStatement prep = getConnection().prepareStatement("INSERT INTO accounts(username, password) value(?, ?)")) {

            prep.setString(1, username);
            prep.setString(2, passwordEncoder.encode(password));
            prep.execute();
        }
    }

    protected void unExecute() throws SQLException {
        PreparedStatement prep = getConnection().prepareStatement("DELETE FROM accounts where username = ?");

        prep.setString(1, username);
        prep.execute();
    }
}
