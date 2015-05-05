package examportal.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import examportal.dto.Test;
import examportal.dto.Users;

public class TestService {
	private String subject=null;
	Connection dbConn;
	ResultSet rs;
	Statement st;
	Test test = new Test();
	public TestService() {
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

	public void setSubject(String sub) {
		subject=sub;
	}

	public Test getTest() {
		subject=subject.toUpperCase();
		int ob1,ob2,ob3,tf1,tf2;
		Random rnd = new Random();
		ob1=rnd.nextInt(9)+1;
		ob2=rnd.nextInt(9)+1;
		while(true) {
			if(ob2==ob1) ob1=rnd.nextInt(9)+1;
			else break;
		}
		ob3=rnd.nextInt(9)+1;
		while(true) {
			if(ob3==ob1 || ob3==ob2) ob1=rnd.nextInt(9)+1;
			else break;
		}
		tf1=rnd.nextInt(9)+1;
		tf2=rnd.nextInt(9)+1;
		while(true) {
			if(tf2==tf1) tf1=rnd.nextInt(9)+1;
			else break;
		}
		try {
			rs=st.executeQuery("SELECT * FROM QOBJ WHERE SUBID='"+subject+"'");
			rs.absolute(ob1);
			test.setQ1(rs.getString("QUES"));
			test.setQ1opa(rs.getString("OPTA"));
			test.setQ1opb(rs.getString("OPTB"));
			test.setQ1opc(rs.getString("OPTC"));
			test.setQ1opd(rs.getString("OPTD"));
			test.setA1(rs.getString("ANS"));
			rs.absolute(ob2);
			test.setQ2(rs.getString("QUES"));
			test.setQ2opa(rs.getString("OPTA"));
			test.setQ2opb(rs.getString("OPTB"));
			test.setQ2opc(rs.getString("OPTC"));
			test.setQ2opd(rs.getString("OPTD"));
			test.setA2(rs.getString("ANS"));
			rs.absolute(ob3);
			test.setQ3(rs.getString("QUES"));
			test.setQ3opa(rs.getString("OPTA"));
			test.setQ3opb(rs.getString("OPTB"));
			test.setQ3opc(rs.getString("OPTC"));
			test.setQ3opd(rs.getString("OPTD"));
			test.setA3(rs.getString("ANS"));
			rs.close();
			rs=st.executeQuery("SELECT * FROM QTF WHERE SUBID='"+subject+"'");
			rs.absolute(tf1);
			test.setQ4(rs.getString("QUES"));
			test.setA4(rs.getString("ANS"));
			rs.absolute(tf2);
			test.setQ5(rs.getString("QUES"));
			test.setA5(rs.getString("ANS"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return test;
	}

	public int getScore(String ans1,String ans2,String ans3,String ans4,String ans5) {
		int score=0;
		if(ans1==null) ans1="X";
		if(ans2==null) ans2="X";
		if(ans3==null) ans3="X";
		if(ans4==null) ans4="X";
		if(ans5==null) ans5="X";
		if(ans1.equalsIgnoreCase(test.getA1())) score++;
		if(ans2.equalsIgnoreCase(test.getA2())) score++;
		if(ans3.equalsIgnoreCase(test.getA3())) score++;
		if(ans4.equalsIgnoreCase(test.getA4())) score++;
		if(ans5.equalsIgnoreCase(test.getA5())) score++;
		return score;
	}
	
	public void setTuple(int score, Users user) {
		String dnowstr=null;
		String testid=user.getUserid().substring(0,7);
		java.util.Date dnow = new java.util.Date();
		SimpleDateFormat testtimestamp = new SimpleDateFormat("ddMMyyhhmmss");
		testid = testid+testtimestamp.format(dnow);
		SimpleDateFormat tjdate = new SimpleDateFormat("dd/MM/yyyy");
		dnowstr = tjdate.format(dnow);
		java.util.Date jdate=null;
		
		try {
			jdate = new SimpleDateFormat("dd/MM/yyyy").parse(dnowstr);
			java.sql.Date tsdate = new java.sql.Date(jdate.getTime());
			rs = st.executeQuery("SELECT * FROM TEST");
			rs.next();
			rs.moveToInsertRow();
			rs.updateString("TESTID",testid);
			rs.updateString("SID",user.getUserid());
			rs.updateString("SUBID", subject);
			rs.updateInt("NQUES", 5);
			rs.updateInt("NCRRTQ", score);
			rs.updateDate("TDATE",tsdate);
			rs.insertRow();
			rs.moveToCurrentRow();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
