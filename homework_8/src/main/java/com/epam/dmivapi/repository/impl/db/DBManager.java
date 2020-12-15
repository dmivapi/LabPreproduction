package com.epam.dmivapi.repository.impl.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
