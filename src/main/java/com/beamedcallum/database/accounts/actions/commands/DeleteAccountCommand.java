package com.beamedcallum.database.accounts.actions.commands;

import com.beamedcallum.database.Database;
import com.beamedcallum.database.DatabaseCommand;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAccountCommand extends DatabaseCommand {
    private String username;

    public DeleteAccountCommand(Database databaseService, String username) {
        super(databaseService);
        this.username = username;
    }

    protected void execute() throws SQLException {
        PreparedStatement prep = getConnection().prepareStatement("DELETE FROM accounts where username = ?");

        prep.setString(1, username);
        prep.execute();
    }

    protected void unExecute() throws SQLException {
        PreparedStatement prep = getConnection().prepareStatement("INSERT INTO accounts(username, password, expiredPassword) VALUES (?, ?, ?)");

        prep.setString(1, username);
        prep.setString(2, null);
        prep.setBoolean(3, true);
        prep.execute();
    }
}
