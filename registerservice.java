package examportal.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import examportal.dto.Users;
public class RegisterService {
	Connection dbConn;
	ResultSet rs;
	Statement st;
	Users user=new Users();
	public RegisterService() {
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
	
	public boolean userAvail(String userid) throws SQLException {
		rs = st.executeQuery("SELECT * FROM USERS WHERE SID='"+userid+"'");
		if(rs.next()) return true;
		return false;
	}

	public void regUser(String userid, String name, String sex, String dobs, String email, int xp, int xiip, String[] sub, long cnt, String pass) throws SQLException, ParseException{
		int i=0;
		java.util.Date jdate = new SimpleDateFormat("dd/MM/yyyy").parse(dobs);
		java.sql.Date sdate = new java.sql.Date(jdate.getTime());
		rs = st.executeQuery("SELECT * FROM USERS");
		rs.next();
		rs.moveToInsertRow();
		rs.updateString("SID",userid);
		rs.updateString("NAME",name);
		rs.updateString("SEX",sex);
		rs.updateString("DOB", dobs);
		rs.updateDate("DOB", sdate);
		rs.updateLong("CONTACT", cnt);
		rs.updateInt("X", xp);
		rs.updateInt("XII", xiip);
		rs.updateString("EMAIL",email);
		rs.updateString("PASSWORD",pass);
		for(i=0; i<sub.length; i++) {
			if(sub[0]==null) rs.updateString("SUB"+(1+i),"-");
			else rs.updateString("SUB"+(1+i),sub[i]);
		}
		rs.insertRow();
		rs.moveToCurrentRow();
		rs.close();
	}
}
