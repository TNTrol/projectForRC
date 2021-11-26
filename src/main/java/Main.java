import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/shop_rc", "tntrol", "dfkjd");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
