package DBS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtils {
	
	public static Connection connectDatabase () throws Exception{

		try {
			System.out.print("wait a moment while loading data base");
			String url = "jdbc:mysql://localhost:3306/DBS";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url, "root","");
			
			Statement stat = con.createStatement();
			System.out.print("show the result");
				
				return con;
	}
		catch (ClassNotFoundException e) {
			System.out.print("Error");
			return null;
			
		
	}
	}	
	
}
