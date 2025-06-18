package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TestListStudent;

public class TestListStudentDao {

	private List<TestListStudent> postFilte(ResultSet rSet)throws Exceotion{
		List<TestListStudent>list=new ArrayList<>();
		try{
			while(rSet.next()){
				TestListStudent testliststudent=new TestListStudent();
				testliststudent.setNo(rSet.getString("no"));
				testliststudent.setName(rSet.getString("name"));
				testliststudent.setEntYear(rSet.getInt("ent_year"));
				testliststudent.setClassNum(rSet.getString("class_num"));
				testliststudent.setAttend(rSet.getBoolean("is_attend"));
				list.add(testliststudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;

	}
}
