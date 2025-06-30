package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@SuppressWarnings("null")
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		int subjectCode = 0;
		String subjectName = "";


		// リクエストパラメーターの取得 2
		subjectCode = Integer.parseInt(req.getParameter("subjectCode"));
		subjectName = req.getParameter("subjectName");


		// DBからデータ取得 3
		// なし


		// studentに学生情報をセット

		Student subject = null;
		subject.setName(subjectName);
		subject.setEntYear(subjectCode);



		// レスポンス値をセット 6
		// なし

		// JSPへフォワード 7
		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}

}