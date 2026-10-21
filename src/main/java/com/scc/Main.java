package com.scc;

import dao.DatabaseConnection;
import ui.DormMenu;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();
        new DormMenu().start();
    }
}