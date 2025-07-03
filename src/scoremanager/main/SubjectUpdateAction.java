package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		String no = ""; // 学生番号
		String subjectName= ""; // 氏名
		int subjectCode = 0; // 入学年度
		Student student = new Student();
		StudentDao studentDao = new StudentDao();

		// リクエストパラメーターの取得 2
		no = req.getParameter("no");

		// DBからデータ取得 3
		// 学生の詳細データを取得
		student = studentDao.get(no);
		// ビジネスロジック 4
		subjectCode = student.getEntYear();
		subjectName = student.getName();

		// レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("subjectCode", subjectCode);
		// リクエストに氏名をセット
		req.setAttribute("subjectName", subjectName);


		// JSPへフォワード 7
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}

}
