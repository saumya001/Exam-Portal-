package examportal;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import examportal.dto.Users;
import examportal.service.LoginService;
@WebServlet("/loginauth")
public class loginservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid=request.getParameter("userid");
		String password=request.getParameter("pass");
		LoginService loginservice = new LoginService();
		try {
			int result = loginservice.authenciate(userid, password);
			if(result==1) {
				Users user = loginservice.getUserDetails(userid);
				request.getSession().setAttribute("userobj", user);
				response.sendRedirect("/Exam_Portal/dashboard");
				return;
			} else {
				request.getSession().setAttribute("stat", result);
				response.sendRedirect("/Exam_Portal/login");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
