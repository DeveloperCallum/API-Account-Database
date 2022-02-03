package com.beamedcallum.database.accounts;

import com.beamedcallum.database.Database;
import com.beamedcallum.database.DatabaseCommand;
import com.beamedcallum.database.accounts.models.Account;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveAccountCommand extends DatabaseCommand {
    private final Database databaseService;
    private Account account;

    public SaveAccountCommand(Database databaseService, Account account) {
        super(databaseService);
        this.databaseService = databaseService;
        this.account = account;
    }

    @Override
    protected void execute() throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("insert into accounts(username, password, roles, enabled, expiredPassword) VALUES (?, ?, ?, ?, ?)");

        if (account.getUsername().isBlank()){
            throw new RuntimeException("Username Cannot be blank");
        }

        if (account.getPassword().isBlank()){
            throw new RuntimeException("Password Cannot be blank");
        }

        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.setString(3, account.getRoleCSV());
        preparedStatement.setBoolean(4, account.isEnabled());
        preparedStatement.setBoolean(5, account.isPasswordExpired());

        preparedStatement.execute();
    }

    @Override
    protected void unExecute() throws SQLException {
        DeleteAccountCommand deleteAccountCommand = new DeleteAccountCommand(databaseService, account.getUsername());
        deleteAccountCommand.execute();
    }
}
