package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {

	
	public void execute1(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
	
        HttpServletRequest request;
        HttpServletResponse response;
    ) 
	}
    throws Exception {

        // フォーム取得
        TestForm testForm = (TestForm) form;

        // セッションからユーザーデータ取得（仮）
        String userId = (String) request.getSession().getAttribute("userId");
        String schoolCode = (String) request.getSession().getAttribute("schoolCode");

        // DAOで必要なデータを取得
        ClassDao classDao = new ClassDao();
        SubjectDao subjectDao = new SubjectDao();

        request.setAttribute("classList", classDao.getClassList(schoolCode));
        request.setAttribute("subjectList", subjectDao.getSubjectList(schoolCode));

        return mapping.findForward("success");
    }

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
