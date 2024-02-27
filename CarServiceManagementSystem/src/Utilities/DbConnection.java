package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection
{
    static Connection conn = null;

    public static Connection getConnection()
    {
        try
        {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/C:\\Users\\ozi25\\Desktop\\Databases\\CarServiceDb", "sa", "123");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }

        return conn;
    }
}