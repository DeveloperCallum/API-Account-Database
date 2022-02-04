package com.beamedcallum.database.accounts.actions.queries;

import com.beamedcallum.database.Database;
import com.beamedcallum.database.DatabaseQuery;
import com.beamedcallum.database.accounts.models.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadAccountQuery extends DatabaseQuery<Account> {
    private String username;

    public ReadAccountQuery(Database databaseService, String username) {
        super(databaseService);
        this.username = username;
    }

    protected PreparedStatement prepare() throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * from accounts where username = ?");
        preparedStatement.setString(1, username);

        return preparedStatement;
    }

    protected Account createWrapper(ResultSet queryRes) throws SQLException {
        if (queryRes.next()){
            Account.AccountBuilder builder = new Account.AccountBuilder();

            String username = queryRes.getString("username");
            builder.setUsername(username);

            String password = queryRes.getString("password");
            builder.setPassword(password);

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
