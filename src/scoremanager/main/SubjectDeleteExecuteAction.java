package scoremanager.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.SubjectDao;

/**
 * 科目削除実行アクション
 *
 * <p>
 *   画面から送信された subjectCode を基に Subject 情報を削除します。
 *   成功時は一覧画面 (SubjectListAction) へリダイレクトし、
 *   失敗時は同じ画面へフォワードしてエラーメッセージを表示します。
 * </p>
 */
@WebServlet("/SubjectDeleteExecuteAction")
public class SubjectDeleteExecuteAction extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 文字コードの設定
        request.setCharacterEncoding("UTF-8");

        // ログインチェック
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // 未ログインの場合はログイン画面へ
            response.sendRedirect(request.getContextPath() + "/LoginAction");
            return;
        }
        User loginUser = (User) session.getAttribute("user");

        // リクエストパラメータ取得
        String subjectCode = request.getParameter("subjectCode");
        if (subjectCode == null || subjectCode.isEmpty()) {
            // パラメータ不足
            request.setAttribute("error", "削除対象の科目コードが取得できませんでした。");
            request.getRequestDispatcher("/SubjectDelete.jsp").forward(request, response);
            return;
        }

        SubjectDao dao = new SubjectDao();
        try {
            int deleted = dao.delete(subjectCode, loginUser.getUserId()); // 実装に合わせてシグネチャを変更してください

            if (deleted > 0) {
                // 削除成功
                response.sendRedirect(request.getContextPath() + "/SubjectListAction?deleted=true");
            } else {
                // 該当科目なし
                request.setAttribute("error", "指定された科目は存在しません。");
                request.getRequestDispatcher("/SubjectDelete.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "科目の削除に失敗しました。");
            request.getRequestDispatcher("/SubjectDelete.jsp").forward(request, response);
        }
    }

    /**
     * GET アクセスは一覧画面へリダイレクト
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/SubjectListAction");
    }
}
