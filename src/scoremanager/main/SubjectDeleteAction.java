package scoremanager.main;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.User;
import dao.SubjectDao;

@WebServlet("/SubjectDeleteAction")
public class SubjectDeleteAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String subjectCode = request.getParameter("subjectCode");
        String schoolCode = user.getSchoolCode();

        SubjectDao dao = new SubjectDao();
        Subject old = dao.getSubject(subjectCode, schoolCode);

        Object subject = null;
		if (subject == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        request.setAttribute("subject", subject);
        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}
