package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.Test;

public class TestDao extends Dao {

    public List<Test> filter(Subject subject, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();

        String sql = "SELECT * FROM test WHERE subject_cd = ? AND school_cd = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, subject.getCd());
        statement.setString(2, school.getCd());

        ResultSet rset = statement.executeQuery();
        while (rset.next()) {
            Test test = new Test();
            test.setNo(rset.getInt("no"));
            test.setSubject(subject);
            test.setSchool(school);
            test.setPoint(rset.getInt("point"));
            list.add(test);
        }

        statement.close();
        connection.close();
        return list;
    }

    public boolean save(Test test, Connection connection) throws Exception {
        boolean result = true;

        String sql = "INSERT INTO test (no, subject_cd, school_cd, point) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, test.getNo());
        statement.setString(2, test.getSubject().getCd());
        statement.setString(3, test.getSchool().getCd());
        statement.setInt(4, test.getPoint());

        int rows = statement.executeUpdate();
        if (rows != 1) result = false;

        statement.close();
        return result;
    }
}
