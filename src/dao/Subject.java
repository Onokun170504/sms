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

public class Subject extends Dao {

    //get 科目コードと学校コードから一件の科目データを取得

    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?");
            statement.setString(1, school.getCd());
            statement.setString(2, cd);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
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
        List<Subject> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?");
            statement.setString(1, school.getCd());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setCd(resultSet.getString("CD"));
                subject.setName(resultSet.getString("NAME"));
                subject.setSchool(SchoolDao.get(resultSet.getString("SCHOOL_CD")));
                list.add(subject);
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
        return list;
    }

  //-------------------------------------------------------------------------------

    //Beanには追加と変更したい科目のデータ(学校, 科目コード, 名前)が設定
    public boolean save(Subject subject) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = getConnection();

            // SchoolDaoはgetメソッド内で使われているため、ここでインスタンス化する必要はありません
            // Subject old = get(subject.getCd(), schoolDao.get(subject.getCd())); // 以前の記述
            Subject old = get(subject.getCd(), subject.getSchool());

            if (old == null) {
                // 科目が存在しなかった場合、科目を新規作成
                statement = connection.prepareStatement("INSERT INTO SUBJECT(SCHOOL_CD, CD, NAME) VALUES(?, ?, ?)");
                statement.setString(1, subject.getSchool().getCd());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getName());
            } else {
                // 科目が存在した場合、科目名を変更
                statement = connection.prepareStatement("UPDATE SUBJECT SET NAME = ? WHERE SCHOOL_CD = ? AND CD = ?");
                statement.setString(1, subject.getName());
                statement.setString(2, subject.getSchool().getCd());
                statement.setString(3, subject.getCd());
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
    public boolean delete(Subject subject) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = getConnection();
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
}