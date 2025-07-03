package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションから教師情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 学校コードを使って科目一覧を取得
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filterBySchool(teacher.getSchool());

        // 取得した科目リストをリクエスト属性にセット
        req.setAttribute("subjectList", subjectList);

        // JSPにフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
