package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		/* ローカル変数の宣言 */
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String isAttendStr = ""; // 入力された在学フラグ
		int entYear = 0; // 入学年度
		boolean isAttend = false; // 在学フラグ
		List<Student> students = null; // 学生リスト
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得
		StudentDao studentDao = new StudentDao(); // 学生Dao
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ




		/*
		 *  リクエストパラメータの取得
		 *  (JSPファイルから受け取る値やデータ)
		 */




		/* DBからデータを取得する */




		/*
		 * レスポンスをセットする
		 * (JSPファイルに渡したい値やデータ)
		 */



		/* jspファイルの表示 */
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);



	}
}
