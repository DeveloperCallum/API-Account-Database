package com.beamedcallum.database.accounts.actions.commands;

import com.beamedcallum.database.Database;
import com.beamedcallum.database.DatabaseCommand;
import com.beamedcallum.database.accounts.models.Account;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccountCommand extends DatabaseCommand {
    private String username;
    private String encryptedPassword;
    private String roleCSV;
    private boolean enabled = true;

    public CreateAccountCommand(Database databaseService, String username, String encryptedPassword, String roleCSV) {
        super(databaseService);
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.roleCSV = roleCSV;
    }

    public CreateAccountCommand(Database databaseService, String username, String encryptedPassword, String roleCSV, boolean enabled) {
        super(databaseService);
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.roleCSV = roleCSV;
        this.enabled = enabled;
    }

    protected void execute() throws SQLException {
        PreparedStatement prep = getConnection().prepareStatement("INSERT INTO accounts(username, password, roles, enabled) VALUES (?, ?, ?, ?)");

        prep.setString(1, username);
        prep.setString(2, encryptedPassword);
        prep.setString(3, roleCSV);
        prep.setBoolean(4, enabled);
        prep.execute();
    }

    protected void unExecute() throws SQLException {
        PreparedStatement prep = getConnection().prepareStatement("DELETE FROM accounts where username = ?");

        prep.setString(1, username);
        prep.execute();
    }
}
