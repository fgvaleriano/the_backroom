package edu.tangingina.thebackroom.util;

import java.io.*;
import java.sql.*;

public class ExportManager {

    public static void exportData (DatabaseManager dm, String filePath, String selectedType) {
        System.out.println("Preparing to save data");

        try {
            dm.getConnection();
            Connection conn = dm.conn;

            String query = "Select * from media";

            if (selectedType != null && !selectedType.equals("All Media")) {
                query += " where media_type = ?";
            }

            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 PrintWriter out = new PrintWriter(new File(filePath))) {

                if (selectedType != null && !selectedType.equals("All Media")) {
                    pstmt.setString(1, selectedType);
                }

                ResultSet rs = pstmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columns = rsmd.getColumnCount();

                for (int i = 1; i <= columns; i++) {
                    out.print(rsmd.getColumnName(i) + (i < columns ? ", " : ""));
                }
                out.println();

                while (rs.next()) {
                    for (int i = 1; i <= columns; i++) {
                        String val = rs.getString(i);
                        out.print((val != null ? val.replace(",", ";"):"") + (i < columns ? "," : ""));
                    }
                    out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
