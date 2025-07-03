//- get(cd: String, school: School): Subject
//- filter(school: School): List<Subject>
//- save(subject: Subject): boolean
//- delete(subject: Subject): boolean
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    //get で科目コード＆学校コードで一件の科目データを取得することができるようにする。

    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;// 取得した科目データを格納するSubjectオブジェクトを作成

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();// データベース接続を取得
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?");
            statement.setString(1, school.getCd());
            statement.setString(2, cd);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {// 結果セットに次の行がある
                subject = new Subject();
                subject.setCd(resultSet.getString("CD"));
                subject.setName(resultSet.getString("NAME"));
                subject.setSchool(SchoolDao.get(resultSet.getString("SCHOOL_CD")));
            }

        } catch (Exception e) {
            throw e;
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return subject;
    }

    //-------------------------------------------------------------------------------
    // setter/getterメソッドはデータベースから取得したデータを格納する
    // これらのメソッドは、bean.Subjectクラスに移動する必要があります。

    private void setSchool(Object object) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void setName(String string) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void setCd(String string) {
		// TODO 自動生成されたメソッド・スタブ

	}
	//-------------------------------------------------------------------------------

    //学校Beanを指定
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();//取得した科目データを格納するリス

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();//データベース接続を取得
            //学校コードに一致する全ての科目をSUBJECTテーブルから検索する
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?");
            statement.setString(1, school.getCd());
            resultSet = statement.executeQuery();

            while (resultSet.next()) { //全ての該当データを処理
                Subject subject = new Subject(); // 新しいSubjectオブジェクトを生成
                subject.setCd(resultSet.getString("CD"));// 科目コードを設定
                subject.setName(resultSet.getString("NAME"));// 科目名を設定
                subject.setSchool(SchoolDao.get(resultSet.getString("SCHOOL_CD")));
                list.add(subject);
            }
        } catch (Exception e) {
            throw e; // 例外が発生した場合、呼び出し元に再スロー
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return list; // 科目リストを返却
    }

  //-------------------------------------------------------------------------------

    //Beanには追加と変更したい科目のデータ(学校, 科目コード, 名前)が設定
    public boolean save(Subject subject) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = getConnection(); // データベース接続を取得
            Subject old = get(subject.getCd(), subject.getSchool());

            if (old == null) {
                // 科目が存在しなかった場合、科目を新規作成
                statement = connection.prepareStatement("INSERT INTO SUBJECT(SCHOOL_CD, CD, NAME) VALUES(?, ?, ?)");
                statement.setString(1, subject.getSchool().getCd());// 学校コードを設定
                statement.setString(2, subject.getCd());// 科目コードを設定
                statement.setString(3, subject.getName());// 科目名を設定
            } else {
                // 科目が存在した場合、科目名を変更
                statement = connection.prepareStatement("UPDATE SUBJECT SET NAME = ? WHERE SCHOOL_CD = ? AND CD = ?");
                statement.setString(1, subject.getName());// 新しい科目名を設定
                statement.setString(2, subject.getSchool().getCd());// 更新条件となる学校コードを設定
                statement.setString(3, subject.getCd()); //更新条件となる科目コードを設定
            }
            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return count > 0;
    }

  //-------------------------------------------------------------------------------

    private String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	private School getSchool() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	private String getCd() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	//-------------------------------------------------------------------------------

	//科目Beanを指定 渡すBeanにはデータ,学校, 科目コード設定
    public boolean delete(SubjectDao subject) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = getConnection(); // データベース接続を取得
            Subject old = get(subject.getCd(), subject.getSchool());

            if (old != null) {
                statement = connection.prepareStatement("DELETE FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?");
                statement.setString(1, subject.getSchool().getCd());
                statement.setString(2, subject.getCd());
                count = statement.executeUpdate();
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return count > 0;
    }

<<<<<<< HEAD
	public List<Subject> filterBySchool(School school) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
=======
	public Subject getSubject(String subjectCode, String schoolCode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public int delete(String subjectCode, Object userId) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
>>>>>>> branch 'master' of https://github.com/Onokun170504/sms.git
	}
}