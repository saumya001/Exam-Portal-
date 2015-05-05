package examportal.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import examportal.dto.Users;
public class LoginService {
	Connection dbConn;
	ResultSet rs;
	Statement st;
	Users user=new Users();
	public LoginService() {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			System.out.println("Driver loaded");
			dbConn = DriverManager.getConnection("jdbc:db2://localhost:50000/EXPOR", "Ranjan Agrawal", "2635");
			if(dbConn!=null) System.out.println("Connected");
			st = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);;
		} catch(SQLException s) {
			System.out.println("SQLException : Connection not Established");
		} catch(ClassNotFoundException  C) {
			System.out.println("ClassNotFoundException");
		}
	}

	public int authenciate (String userid, String pass) throws SQLException {
		int i=0;
		userid=userid.toUpperCase();
		rs = st.executeQuery("SELECT * FROM USERS WHERE SID='"+userid+"'");
		while(rs.next()) {
			if(pass.equals(rs.getString("PASSWORD"))) {
				user.setUserid(rs.getString("SID"));
				user.setUsername(rs.getString("NAME"));
				user.setUserpass(rs.getString("PASSWORD"));
				i=1;
			}
			else i=2;
		}
		rs.close();
		st.close();
		dbConn.close();
		return i;
	}

	public Users getUserDetails(String userid) {
		return user;
	}
}
