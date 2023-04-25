package ca.sheridancollege.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import ca.sheridancollege.beans.Student;
@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;


	public void addStudent(Student student) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO STUDENTS  (name)"
				+ " VALUES (:name)";
		parameters.addValue("name", student.getName());
		jdbc.update(query, parameters);

	}

	public  ArrayList<Student> getStudents(){
		ArrayList<Student> students = new ArrayList<Student>();
		String query = "SELECT * FROM STUDENTS";
		List<Map<String, Object>> rows = jdbc.queryForList(query, new HashMap<String, Object>());
		for(Map<String, Object> row: rows) {
			Student s = new Student();
			s.setId((Integer)row.get("id"));
			s.setName((String)row.get("name")); 

			students.add(s);
		}
		return students;
	}

	public  Student getStudentById(int id){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		ArrayList<Student> students = new ArrayList<Student>();
		String query = "SELECT * FROM STUDENTS WHERE id=:id";

		parameters.addValue("id", id);

		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for(Map<String, Object> row: rows) {
			Student s = new Student();
			s.setId((Integer)row.get("id"));
			s.setName((String)row.get("name")); 

			students.add(s);
		}
		if (students.size()>0)
			return students.get(0);
		return null;
	}
	
	public void deleteStudents() {
		String query = "DELETE FROM STUDENTS";
		jdbc.update(query, new HashMap());
	}
	
	public void deleteStudentElement(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM STUDENTS WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}
	
	public void resetIndex() {
		String query = "ALTER TABLE STUDENTS ALTER COLUMN id RESTART WITH 1";
		jdbc.update(query, new HashMap());
	}

	public void changeStudent(Student student, long id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String q = "UPDATE STUDENTS SET name=:name WHERE id=:id";
		parameters.addValue("id", id);
		parameters.addValue("name", student.getName());
		jdbc.update(q, parameters);
		
	}
}
