package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.Student;
import bean.Test;


//Daoクラスを継承
public class TestDao extends Dao {
	/**
	 * 指定された学生・科目・学校の情報をもとに、該当するテスト情報を取得します。
	 *
	 * @param student 対象の学生オブジェクト
	 * @param subject 対象の科目オブジェクト
	 * @param school 対象の学校オブジェクト
	 * @return 条件に一致する Test オブジェクトのリスト
	 * @throws Exception データベースアクセス時にエラーが発生した場合
	 */

	public List<Test> get(Student student, Subject subject, School school) throws Exception {
		  // 結果を格納するためのリストを初期化
		List<Test> list = new ArrayList<>();
		// データベースコネクションを確立
	    Connection connection = getConnection();
	    //test テーブルから、指定した条件に一致するデータを取得する
	    String sql = "SELECT * FROM test WHERE school=? AND class_num=? AND subject=? AND num=?";
	    //sql文に値をバインドして実行
	    PreparedStatement statement = connection.prepareStatement(sql);
	    // プリペアードステートメントのプレースホルダに値をバインド
	    statement.setString(1, school.getCd());//学校名を設定
	    statement.setInt(2, student.getClassNum().getNum());//学生名を設定
	    statement.setString(3, subject.getCd());//科目を設定
	    statement.setInt(4, student.getNo());//名前を設定
	    // SQLを実行し、結果をResultSetに取得
	    ResultSet resultSet = statement.executeQuery();
	    //DBから取得したデータを1行ずつ読む
	    while (resultSet.next()) {
	       //1行ずつ Test オブジェクトに変換する
	    	Test test = new Test();
	    	//リストに追加する
	        test.setId(resultSet.getInt("id"));
	        test.setSubject(subject);
	        test.setSchool(school);
	        test.setNum(resultSet.getInt("num"));
	        //リストの処理
	        list.add(test);
	    }

	    return list; // フィルタリングされたリストを返却
	}

	/**
	 * ResultSet からデータを読み込み、Test オブジェクトのリストに変換します。
	 *
	 * @param rSet データベースから取得した結果セット
	 * @param school 対象の学校オブジェクト（各Testに紐づけ）
	 * @return ResultSet から生成された Test オブジェクトのリスト
	 * @throws Exception データ読み込み中にエラーが発生した場合
	 */
	private List<Test> postFilter(ResultSet rSet,School school)throws Exception{
		 // 結果を格納するためのリストを初期化
		List<Test>list=new ArrayList<>();
		try{
			// リザルトセットを1行ずつ全件走査します
			while(rSet.next()){
				// Testオブジェクトと、科目のポイントを格納するMapを初期化
				Test test=new Test();
				 // ResultSetから各列のデータを取得し、Testオブジェクトにセット
				test.setNo(rSet.getInt("no"));//学生番号
				test.setName(rSet.getString("name"));//名前
				test.setEntYear(rSet.getInt("ent_year"));//入学年度
				test.setClassNum(rSet.getString("class_num"));//クラス番号
				test.setAttend(rSet.getBoolean("is_attend"));//真偽の入力
				test.setSchool(school);//学校名
				 // 完成したTestオブジェクトをリストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			 // SQL例外またはNullPointerExceptionが発生した場合、スタックトレースを出力
			e.printStackTrace();
		}

		return list;// フィルタリングされたリストを返却
	}




	/**
	 * 指定された科目・学校・クラス番号・テスト番号に一致するテスト情報を取得します。
	 *
	 * @param subject 対象の科目オブジェクト
	 * @param school 対象の学校オブジェクト
	 * @param classNum 対象のクラス番号
	 * @param num 対象のテスト番号
	 * @return 条件に一致する Test オブジェクトのリスト
	 * @throws Exception データベースアクセス時にエラーが発生した場合
	 */

	public List<Test> filter(Subject subject, School school, int classNum, int num)throws Exception {//テスト一覧の取得


        List<Test> list = new ArrayList<>(); // 結果を格納するためのリストを初期化
        Connection connection = getConnection();  // データベースコネクションを確立

        String sql = "SELECT * FROM test WHERE school=? AND class_num=? AND subject=? AND num=?";//sql文の条件句を定義
       //sql文の準備
        PreparedStatement statement = connection.prepareStatement(sql);
        // プリペアードステートメントのプレースホルダに値をバインド
        statement.setString(1, school.getCd());//1番目に学校名
        statement.setInt(2, classNum);//2番目にクラス番号
        statement.setString(3, subject.getCd());//3番目に科目
        statement.setInt(4, num);//4番目に点数

        //SQLを実行して、結果を ResultSet として受け取る
        ResultSet resultSet = statement.executeQuery();
        //結果セットを1行ずつ読み込むループ
        while (resultSet.next()) {
        	 // Testオブジェクトを格納するMapを初期化
            Test test = new Test();
            test.setId(resultSet.getInt("id"));
            test.setSubject(subject);
            test.setNum(resultSet.getInt("num"));
            test.setSchool(school);
            list.add(test);
        }

        return list; // 作成されたリストを返却
    }

	/**
	 * 指定された Test オブジェクトのリストを一括でデータベースに保存します。
	 * 1件でも保存に失敗した場合はロールバックし、false を返します。
	 *
	 * @param list 保存対象の Test オブジェクトのリスト
	 * @return すべての保存に成功した場合は true、失敗があった場合は false
	 * @throws Exception データベース操作中にエラーが発生した場合
	 */

	public boolean saveList(List<Test> list) throws Exception {
		Connection connection = getConnection();// データベースコネクションを確立
		boolean result = true;

		try {
			connection.setAutoCommit(false); // トランザクション開始

			for (Test test : list) {
				if (!save(test, connection)) {
					result = false;
					System.err.println("保存失敗: Test ID = " + test.getId()); // ログ出す
					break; // 失敗したらループやめる（任意）
				}
			}

			if (result) {
				connection.commit(); // 全部成功→コミット
			} else {
				connection.rollback(); // 1つでも失敗→ロールバック
			}

		} catch	 (Exception e) {
			connection.rollback(); // 例外でもロールバック
			throw e; // 呼び出し元に投げる
		} finally {
			if (connection != null) connection.close(); // 接続クローズ
		}

		return result;
	}
	/**
	 * 指定された Test オブジェクトをデータベースに保存します。
	 *
	 * @param test 保存対象の Test オブジェクト
	 * @param connection 使用するデータベース接続
	 * @return 保存に成功した場合は true、失敗した場合は false
	 * @throws SQLException SQL 実行中にエラーが発生した場合
	 */

	public boolean save(Test test, Connection connection) throws SQLException {
		String sql = "INSERT INTO test (subject, num, school) VALUES (?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, test.getSubject().getId());
			statement.setInt(2, test.getNum());
			statement.setString(3, test.getSchool().getId());
			int rows = statement.executeUpdate();
			return rows > 0;
		}
	}

	public void insertTestData(String grade, String className, String subject, int score) {
		// TODO 自動生成されたメソッド・スタブ

	}



}