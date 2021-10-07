import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JFrame;

public class DbConnect extends JFrame {

    private Connection con;
    private String url;
    private String user;
    private String pass;
    private String sqlitePath;

    private Connection Connect() {
        try {
            sqlitePath = "org.sqlite.JDBC";
            url = "jdbc:sqlite:tictactoe.db";
            Class.forName(sqlitePath);
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.out.println("jar file : " + e);
        } catch (SQLException ex) {
            System.out.println("ex.getCause() : " + ex.getCause());
            System.out.println("ex.getMessage() : " + ex.getMessage());
        }
        return con;
    }

    protected Connection db() {
        return Connect();
    }
}