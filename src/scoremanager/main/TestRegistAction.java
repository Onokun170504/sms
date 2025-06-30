package scoremanager.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TestDao;

public class TestRegistAction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 入力値取得
        String grade = request.getParameter("grade");
        String className = request.getParameter("class");
        String subject = request.getParameter("subject");
        String scoreStr = request.getParameter("score");

        int score = Integer.parseInt(scoreStr);

        // バリデーションとかやる
        if (score < 0 || score > 100) {
            request.setAttribute("error", "0〜100の範囲で入力してください");
            request.getRequestDispatcher("/test_regist.jsp").forward(request, response);
            return;
        }

        // DAO使ってDB保存する処理
        TestDao dao = new TestDao();
        dao.insertTestData(grade, className, subject, score);

        // 登録完了画面へ
        response.sendRedirect("test_regist_done.jsp");
    }
}