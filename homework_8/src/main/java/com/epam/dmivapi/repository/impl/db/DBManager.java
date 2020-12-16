package com.epam.dmivapi.repository.impl.db;

import java.sql.Connection;

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
//TODO: check if we need this
//        try {
//            con.rollback();
//            con.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
    }

    public void commitAndClose(Connection con) {
//        try {
//            con.commit();
//            con.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
    }
}
