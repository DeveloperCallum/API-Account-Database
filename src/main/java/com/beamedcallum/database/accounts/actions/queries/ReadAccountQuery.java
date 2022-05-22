package com.beamedcallum.database.accounts.actions.queries;

import com.beamedcallum.database.Database;
import com.beamedcallum.database.DatabaseQuery;
import com.beamedcallum.database.accounts.models.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadAccountQuery extends DatabaseQuery<Account> {
    private final String username;
    private boolean getPassword;

    public ReadAccountQuery(Database databaseService, String username) {
        super(databaseService);
        this.username = username;
    }

    public ReadAccountQuery(Database databaseService, String username, boolean getPassword) {
        super(databaseService);
        this.username = username;
        this.getPassword = getPassword;
    }

    protected PreparedStatement prepare() throws SQLException {
        PreparedStatement preparedStatement;

        if (getPassword) {
            preparedStatement = getConnection().prepareStatement("SELECT * from accounts where username = ?");
            preparedStatement.setString(1, username);
        } else {
            preparedStatement = getConnection().prepareStatement("SELECT username, created_at, enabled, roles, enabled, expiredPassword from accounts where username = ?");
            preparedStatement.setString(1, username);
        }


        return preparedStatement;
    }

    protected Account createWrapper(ResultSet queryRes) throws SQLException {
        if (queryRes.next()) {
            Account.AccountBuilder builder = new Account.AccountBuilder();

            String username = queryRes.getString("username");
            builder.setUsername(username);

            if (getPassword){
                String password = queryRes.getString("password");
                builder.setPassword(password);
            }

            String roleCSV = queryRes.getString("roles");
            builder.setRoleCSV(roleCSV);

            boolean enabled = queryRes.getBoolean("enabled");
            builder.setEnabled(enabled);

            boolean expiredPassword = queryRes.getBoolean("expiredPassword");
            builder.setPasswordExpired(expiredPassword);

            return builder.build();
        }

        return null;
    }
}
