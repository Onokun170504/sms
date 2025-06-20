package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;
/**
 * 学生ごとのテストリスト情報（TestListStudent）を操作するDAOクラス。
 * データベースから特定の学生に関連するテスト情報を取得する機能を提供する。
 */
public class TestListStudentDao {
    private String baseSql = "SELECT * FROM test_list_student WHERE 1=1";
    /**
     * ResultSet から TestListStudent オブジェクトのリストを生成します。
     * 各行を TestListStudent に変換して、リストに追加します。
     *
     * @param rs データベースクエリの結果セット
     * @return TestListStudent オブジェクトのリスト
     * @throws SQLException データ取得中にエラーが発生した場合
     */
    public List<TestListStudent> postFilter(ResultSet rs) throws SQLException {
        List<TestListStudent> list = new ArrayList<>();
        // リザルトセットを1行ずつ全件走査
        while (rs.next()) {
        	// インスタンスの初期化
            TestListStudent test = new TestListStudent();
            // ResultSetから各列のデータを取得し、TestListStudentオブジェクトにセット
            test.setCd(rs.getString("cd"));
            test.setSubjectName(rs.getString("subjectName"));
            test.setNum(rs.getInt("num"));
            test.setPoint(rs.getInt("point"));
            test.setEnter(rs.getInt("enter"));
         // 完成したTestListStudentオブジェクトをリストに追加
            list.add(test);
        }
        return list; // 作成されたリストを返却
    }
    /**
     * 指定された学生コードに一致するテストリスト情報をデータベースから取得します。
     * 学生コードに基づいてフィルタリングされた TestListStudent のリストを返します。
     *
     * @param student 対象の学生オブジェクト（学生コードを利用）
     * @return 条件に一致する TestListStudent オブジェクトのリスト
     */
    public List<TestListStudent> filter(Student student) {
    	// 結果を格納するためのリストを初期化
        List<TestListStudent> resultList = new ArrayList<>();
        //データベースコネクションを初期化
        Connection conn = null;
        //プリペアードステートメントを初期化
        PreparedStatement stmt = null;
        // リザルトセットを初期化
        ResultSet rs = null;

        try {
        	// データベースコネクションを確立
            conn = Dao.getConnection();
            // 基本SQL文からプリペアードステートメントを作成
            String sql = baseSql + " AND cd = ?";
            //*SQL文を実行準備
            stmt = conn.prepareStatement(sql);
            // プリペアードステートメントのプレースホルダに値をバインド
            stmt.setString(1, student.getCd());// 1番目の?に学生コードを設定
            // SQLを実行し、結果をResultSetに取得
            rs = stmt.executeQuery();
            // 取得したResultSetをpostFilterメソッドに渡し、リストへの格納処理を実行
            resultList = postFilter(rs);


        } catch (SQLException e) {
        	//例外を表示
            e.printStackTrace();
        } finally {
        	try {
        		 // コネクションがnullでなければ閉じる
        	    if (rs != null) rs.close();
        	} catch (SQLException e) {
        		// クローズ時の例外をスロー
        	    e.printStackTrace();
        	}

        	try {
        		 // コネクションがnullでなければ閉じる
        	    if (stmt != null) stmt.close();
        	} catch (SQLException e) {
        		// クローズ時の例外をスロー
        	    e.printStackTrace();
        	}

        	try {
        		 // コネクションがnullでなければ閉じる
        	    if (conn != null) conn.close();
        	} catch (SQLException e) {
        		// クローズ時の例外をスロー
        	    e.printStackTrace();
        	}

        }

        return resultList; // フィルタリングされたリストを返却
    }
}
