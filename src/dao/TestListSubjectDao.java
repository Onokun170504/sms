package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

//----------------------------------------------------------------------------------------------

public class TestListSubjectDao extends Dao {
	 // 共通のSQL文の基本部分を定義する
	 //学生テーブルからデータを取得(学生、テスト、科目情報を結合して取得を行う）
		private String baseSql = "SELECT s.ent_year, s.student_no, s.student_name, s.class_num, t.subject_cd, t.point " +
	                           "FROM student AS s " +"JOIN test AS t ON s.student_no = t.student_no " +
	                           "WHERE s.school_cd = ? ";

	/**
     * ResultSetから取得したデータをTestListSubjectDaoのリスト格納
     *
     * @param rSet データベースからのクエリ結果を含むResultSet
     * @return TestListSubjectDaoオブジェクトのリスト
     * @throws Exception 処理中に発生した例外
     */

    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {

        //結果を格納するリストを初期化
        List<TestListSubject> list = new ArrayList<>();

        //同じ学生の複数の科目点数を集約するよう
        Map<String, TestListSubject> studentMap = new HashMap<>(); // ★

        try {
            while (rSet.next()){
            	// 新しいTestListSubjectDaoオブジェクトを作成
                TestListSubjectDao testListSubject = new TestListSubjectDao();
             // 科目番号と点数を格納するためのマップを初期化
                Map<Integer, Integer> points = new HashMap<>();

                //listに検索結果をセットする
                testListSubject.setEntYear(rSet.getInt("ent_year"));
                testListSubject.setStudentNo(rSet.getString("student_no"));
                testListSubject.setStudentName(rSet.getString("student_name"));
                testListSubject.setClassNum(rSet.getString("class_num"));

                // subject_no と point をMap追加する
                int subjectNo = rSet.getInt("subject_no");
                int point = rSet.getInt("point");
                points.put(subjectNo, point);

             // 取得した点数マップをtestListSubjectオブジェクトにセット
                testListSubject.setPoints(points);

             // 作成したTestListSubjectDaoオブジェクトをリストに追加
                list.add(testListSubject);
            }
        } catch (SQLException | NullPointerException e){
            e.printStackTrace();
        }

     // 生成されたリストを返します。
        return list;
    }

  //----------------------------------------------------------------------------------------------
    // TestListSubjectDaoフィールドに値を設定するための仮メソッド。

    private void setPoints(Map<Integer, Integer> points) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void setClassNum(String string) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void setStudentName(String string) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void setStudentNo(String string) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void setEntYear(int int1) {
		// TODO 自動生成されたメソッド・スタブ

	}

	//----------------------------------------------------------------------------------------------

	/**
     * 指定された条件に基づいて学生と試験のリストをフィルタリングして取得します。
     *
     * @param entYear 入学年度
     * @param classNum クラス番号
     * @param subject 科目オブジェクト
     * @param school 学校オブジェクト
     * @return フィルタリングされたTestListSubjectDaoオブジェクトのリスト
     * @throws Exception データベースアクセス中に発生した例外
     */

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		// 結果を格納するためのリストを初期化
        List<TestListSubject> list = new ArrayList<>();
     // データベースコネクションを確立
        Connection connection = null;
        // プリペアードステートメントを初期化
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;
        // SQL文の条件
        String condition = "and ent_year = ? and class_num = ? and subject_cd = ? ";
        // SQL文のソート
        String order = " order by no asc";

        try{
            // プリペアードステートメントにSQL文をセットする
            statement = connection.prepareStatement(baseSql + condition + order);
            // プリペアードステートメントに値をバインド
            // ここではschool_cdが1番目、ent_yearが2番目
            //class_numが3番目、subject_cdが4番目と仮定
            statement.setString(1, school.getCd());
            statement.setString(2, classNum);
            statement.setString(3, classNum);
            statement.setString(4, subject.getCd());

            // プリペアードステートメントを実行
            rSet = statement.executeQuery();
            // リストへの格納処理を実行
            list = postFilter(rSet);

        } catch (Exception e) {
            throw e;
        } finally {
            // プリペアードステートメントを閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            // コネクションを閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return list;
    }
}