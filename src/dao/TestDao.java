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



public class TestDao extends Dao {


	public List<Test> get(Student student, Subject subject, School school) throws Exception {
	    List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();

	    String sql = "SELECT * FROM test WHERE school=? AND class_num=? AND subject=? AND num=?";
	    PreparedStatement statement = connection.prepareStatement(sql);
	    statement.setString(1, school.getCd());
	    statement.setInt(2, student.getClassNum().getNum());
	    statement.setString(3, subject.getCd());
	    statement.setInt(4, student.getNo());

	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
	        Test test = new Test();
	        test.setId(resultSet.getInt("id"));
	        test.setSubject(subject);
	        test.setSchool(school);
	        test.setNum(resultSet.getInt("num"));
	        list.add(test);
	    }

	    return list;
	}

	private List<Test> postFilter(ResultSet rSet,School school)throws Exception{

		List<Test>list=new ArrayList<>();
		try{
			while(rSet.next()){
				Test test=new Test();
				test.setNo(rSet.getInt("no"));
				test.setName(rSet.getString("name"));
				test.setEntYear(rSet.getInt("ent_year"));
				test.setClassNum(rSet.getString("class_num"));
				test.setAttend(rSet.getBoolean("is_attend"));
				test.setSchool(school);
				// リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}




    public List<Test> filter(School school, int classNum, Subject subject, int num) throws Exception {

    public List<Test> filter(Subject subject, School school) throws Exception {
	branch 'master' of https://github.com/Onokun170504/sms.git
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();

        String sql = "SELECT * FROM test WHERE school=? AND class_num=? AND subject=? AND num=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, school.getCd());
        statement.setInt(2, classNum);
        statement.setString(3, subject.getCd());
        statement.setInt(4, num);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Test test = new Test();
            test.setId(resultSet.getInt("id"));
            test.setSubject(subject);
            test.setNum(resultSet.getInt("num"));
            test.setSchool(school);
            list.add(test);
        }

        return list;
    }

    public boolean saveList(List<Test> list) throws Exception {
        Connection connection = getConnection();
        boolean result = true;

        for (Test test : list) {
            if (!save(test, connection)) {
                result = false;
            }
        }

        return result;
    }

    public boolean save(Test test, Connection connection) throws Exception {
        String sql = "INSERT INTO test (subject, num, school) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, test.getSubject().getId());
        statement.setInt(2, test.getNum());
        statement.setString(3, test.getSchool().getId());

        int rows = statement.executeUpdate();
        return rows > 0;
    }
}
