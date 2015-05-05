package examportal;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examportal.service.RegisterService;
@WebServlet("/registervalid")
public class registerservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long contact=123456789l;
		int xperc=0, xiiperc=0,errcode=0;
		String userid=null;
		String name=request.getParameter("name");
		String sex=request.getParameter("sex");
		String dob=request.getParameter("dob");
		String cnt=request.getParameter("cnt");
		if(cnt!=null && cnt!="") contact=Long.parseLong(cnt);
		String xp=request.getParameter("xp");
		if(xp!=null && xp!="") xperc=Integer.parseInt(xp);
		String xiip=request.getParameter("xiip");
		if(xiip!=null && xiip!="") xiiperc=Integer.parseInt(xiip);
		String[] subject=request.getParameterValues("sub");
		String email=request.getParameter("email");
		String reemail=request.getParameter("reemail");
		String pass=request.getParameter("pass");
		String repass=request.getParameter("repass");
		if(name==null || name.trim()=="") errcode=1;
		else if(sex==null) errcode=2;
		else if(dob==null||dob.charAt(2)!='/'||dob.charAt(5)!='/') errcode=3;
		else if(email==null || email.trim()=="" || email.indexOf("@")==-1) errcode=4;
		else if(!(reemail.equalsIgnoreCase(email))) errcode=5;
		else if(xperc<0|| xperc>100) errcode=6;
		else if(xiiperc<0|| xiiperc>100) errcode=7;
		else if(subject==null) errcode=8;
		else if(contact==0||contact<1000000000) errcode=9;
		else if(pass==null) errcode=10;
		else if(!(repass.equals(pass))) errcode=11;
				
		RegisterService reg = new RegisterService();
		if(errcode!=4) {
			userid = email.toUpperCase();
			try {
				boolean i = reg.userAvail(userid);
				if(i) errcode=12;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(errcode==0) {
			try {
				reg.regUser(userid, name, sex, dob, email, xperc, xiiperc, subject, contact, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			response.sendRedirect("/Exam_Portal");
		}
		else {
			request.setAttribute("err", errcode);
			RequestDispatcher dispatcher= request.getRequestDispatcher("/register");
			dispatcher.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*
		if(name==null || name.trim()=="") errcode=1;
		else if(sex==null) errcode=2;
		else if(dob==null||dob.charAt(2)!='/'||dob.charAt(5)!='/') errcode=3;
		else if(email==null || email.trim()=="" || email.indexOf("@")==-1) errcode=4;
		else if(!(reemail.equalsIgnoreCase(email))) errcode=5;
		else if(xperc<0|| xperc>100) errcode=6;
		else if(xiiperc<0|| xiiperc>100) errcode=7;
		else if(subject==null) errcode=8;
		else if(contact==0||contact<1000000000) errcode=9;
		else if(pass==null) errcode=10;
		else if(!(repass.equals(pass))) errcode=11;
				
		RegisterService reg = new RegisterService();
		if(errcode!=4) {
			String userid= email.substring(0, email.indexOf("@"));
			try {
				boolean i = reg.userAvail(userid);
				if(i) errcode=12;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
*/
/*
 		if(name==null || name.trim()=="") errcode[0]=1;
		else if(sex==null) errcode[1]=2;
		else if(dob==null||dob.charAt(2)!='/'||dob.charAt(5)!='/') errcode[2]=3;
		else if(email==null || email.trim()=="") errcode[3]=4;
		else if(reemail.equalsIgnoreCase(email)) errcode[4]=5;
		else if(xperc<0|| xperc>100) errcode[5]=6;
		else if(xiiperc<0|| xiiperc>100) errcode[6]=7;
		else if(subject==null) errcode[7]=8;
		else if(contact==0||contact<1000000000) errcode[8]=9;
		else if(pass==null) errcode[9]=10;
		else if(repass.equals(pass)) errcode[10]=11;
		RegisterService reg = new RegisterService();
		if(errcode[3]!=1) {
			String userid= email.substring(0, email.indexOf("@"));
			try {
				boolean i = reg.userAvail(userid);
				if(i) errcode[11]=1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		for(j=0;j<10;j++) {
			if (errcode[j]!=0) {
				err=1; break;
			}
		}
*/
	}
}
