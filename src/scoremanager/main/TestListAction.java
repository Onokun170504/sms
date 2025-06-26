package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//-------------------------------------------------------------------------

		//①セッションからデータを取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		//②ローカル変数の指定する

		//科目情報---------------------------------------
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String subjectStr = ""; // 入力された科目

		//学生情報---------------------------------------
		String student_no = ""; // 入力された生徒番号

		int entYear = 0; // 入学年度

		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得

		int year = todaysDate.getYear(); // 現在の年を取得

		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		SubjectDao subjectDao = new SubjectDao(); // クラス番号Daoを初期化


		//③リクエストパラメーターを取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectStr = req.getParameter("f3");
		student_no = req.getParameter("f4");


		//④ビジネスロジック
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}


		//⑤リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から現在まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> class_num_set = classNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目名を取得
		List<SubjectDao> subjects = subjectDao.filter(teacher.getSchool());


		//⑥レスポンス値をセット
		// リクエストに入力されたデータをセット
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectStr);
		req.setAttribute("f4", student_no);

		// リクエストにデータをセット
		req.setAttribute("class_num_set", class_num_set);
		req.setAttribute("subjects", subjects);
		req.setAttribute("ent_year_set", entYearSet);


		//⑦JSPへフォワード (test_list.jspに移動)
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}