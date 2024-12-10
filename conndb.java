package LibraryManagement;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class conndb {
    String url = "jdbc:mysql://localhost:3306/library";
    String user = "root";
    String password = "Pass@123@";
    PreparedStatement ps = null;
    public Statement stmt = null;

    public conndb() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
