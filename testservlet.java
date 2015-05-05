package examportal;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examportal.dto.Test;
import examportal.dto.Users;
import examportal.service.TestService;
@WebServlet("/test")
public class testservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TestService tesserv = new TestService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sub=request.getParameter("sub");
		tesserv.setSubject(sub);
		Test tes = null;
		tes = tesserv.getTest();
		request.setAttribute("test", tes);
		RequestDispatcher dispatcher= request.getRequestDispatcher("/testpage");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int score;
		String ans1 = request.getParameter("q1");
		String ans2 = request.getParameter("q2");
		String ans3 = request.getParameter("q3");
		String ans4 = request.getParameter("q4");
		String ans5 = request.getParameter("q5");
		score=tesserv.getScore(ans1,ans2,ans3,ans4,ans5);
		Users user = (Users) request.getSession().getAttribute("userobj");
		tesserv.setTuple(score,user);
		request.setAttribute("testresult", score);
		RequestDispatcher dispatcher= request.getRequestDispatcher("/testresultpage");
		dispatcher.forward(request, response);
	}
}
