package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School; // School Beanをインポート
import bean.Subject; // Subject Beanをインポート
import bean.TestListSubject; // TestListSubject Beanをインポート

// Daoクラスを継承
public class TestListSubjectDao extends Dao {

    // 基本となるSQL文を定義。school_cdを条件に学生情報を取得します。
    private String baseSql = "select * from student where school_cd = ?";

    /**
     * ResultSetからTestListSubjectオブジェクトのリストを作成するプライベートメソッド。
     * データベースから取得した結果を行ごとに処理し、Javaオブジェクトにマッピングします。
     * @param rSet データベースクエリの結果セット
     * @return TestListSubjectオブジェクトのリスト
     * @throws Exception データベースアクセスエラーまたはNullPointerException
     */
    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        // 結果を格納するためのリストを初期化
        List<TestListSubject> list = new ArrayList<>();
        try {
            // リザルトセットを1行ずつ全件走査します
            while (rSet.next()) {
                // TestListSubjectオブジェクトと、科目のポイントを格納するMapを初期化
                TestListSubject testListSubject = new TestListSubject();
                Map<Integer, Integer> points = new HashMap<>();

                // ResultSetから各列のデータを取得し、TestListSubjectオブジェクトにセット
                testListSubject.setEntYear(rSet.getInt("ent_year")); // 入学年度
                testListSubject.setStudentNo(rSet.getString("student_no")); // 学生番号
                testListSubject.setStudentName(rSet.getString("student_name")); // 学生名
                testListSubject.setClassNum(rSet.getString("class_num")); // クラス番号

                // subject_noとpointをMapに追加
                int subjectNo = rSet.getInt("subject_no"); // 科目番号
                int point = rSet.getInt("point"); // 得点
                points.put(subjectNo, point); // Mapに科目番号と得点を追加

                // 作成したpoints MapをTestListSubjectオブジェクトにセット
                testListSubject.setPoints(points);

                // 完成したTestListSubjectオブジェクトをリストに追加
                list.add(testListSubject);
            }
        } catch (SQLException | NullPointerException e) {
            // SQL例外またはNullPointerExceptionが発生した場合、スタックトレースを出力
            e.printStackTrace();
        }
        return list; // 作成されたリストを返却
    }

    /**
     * 指定された条件で学生のテストリスト情報をフィルタリングして取得します。
     * @param entYear 入学年度
     * @param classNum クラス番号
     * @param subject 科目オブジェクト
     * @param school 学校オブジェクト
     * @return フィルタリングされたTestListSubjectオブジェクトのリスト
     * @throws Exception データベースアクセスエラー
     */
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        // 結果を格納するためのリストを初期化
        List<TestListSubject> list = new ArrayList<>();
        // データベースコネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメントを初期化
        PreparedStatement statement = null;
        // リザルトセットを初期化
        ResultSet rSet = null;

        // SQL文に追加する条件句を定義
        String condition = "and ent_year = ? and class_num = ? and subject_cd = ? ";
        // SQL文に追加するソート句を定義（今回はno ascだが、student_noの可能性も）
        String order = " order by no asc"; // ここは恐らくstudent_no ascの間違いかもしれません

        try {
            // 基本SQL文、条件句、ソート句を結合してプリペアードステートメントを作成
            statement = connection.prepareStatement(baseSql + condition + order);

            // プリペアードステートメントのプレースホルダに値をバインド
            statement.setInt(1, entYear); // 1番目の?に入学年度を設定
            statement.setString(2, classNum); // 2番目の?にクラス番号を設定
            statement.setString(3, subject.getCd()); // 3番目の?に科目コードを設定

            // SQLを実行し、結果をResultSetに取得
            rSet = statement.executeQuery();

            // 取得したResultSetをpostFilterメソッドに渡し、リストへの格納処理を実行
            list = postFilter(rSet);

        } catch (Exception e) {
            // 例外が発生した場合、そのままスロー
            throw e;
        } finally {
            // プリペアードステートメントがnullでなければ閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    // クローズ時の例外をスロー
                    throw sqle;
                }
            }
            // コネクションがnullでなければ閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    // クローズ時の例外をスロー
                    throw sqle;
                }
            }
        }
        return list; // フィルタリングされたリストを返却
    }
}