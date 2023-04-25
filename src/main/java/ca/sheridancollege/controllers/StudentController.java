package ca.sheridancollege.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.beans.Student;
import ca.sheridancollege.database.DatabaseAccess;

@RestController
public class StudentController {
	
	@Autowired
	 private DatabaseAccess da;
	@GetMapping("/")
	public String home() {
		return "home";
	}
	//GET-COLLECTION
	@GetMapping("/students")
	public ArrayList<Student> getStudents() {
		return da.getStudents();
	}
	//GET-ELEMENT
	@GetMapping("/students/{id}")
	public Student getStudentById(@PathVariable int id) {
		return da.getStudentById(id);
	}
	//POST-COLLECTION //add a student to a collection
	@PostMapping(value="/students", headers={"Content-type=application/json"})
	public String addStudent(@RequestBody Student student) {
		da.addStudent(student);
		return "Student was added";
		
	}
	
	//PUT-COLLECTION  //deletes entire collection and adds a new collection
		@PutMapping(value="/students", headers={"Content-type=application/json"})
		public String putStudent(@RequestBody List<Student> studentList) {
			da.deleteStudents();
			da.resetIndex();
			for (Student s : studentList) { 
				da.addStudent(s);
			}
			return "Students Added:" + da.getStudents().size();
		}
		
		
		//PUT-ELEMENT   replays element
		@PutMapping(value="/students/{id}", headers={"Content-type=application/json"})
		public String putStudentEl(@PathVariable long id, @RequestBody Student student) {
		//	Student st= da.getStudentById(id);
		//	st.setName(student.getName());
			da.changeStudent(student, id);
			return "Name of student is changed";
			
		}
		
		//DELETE-COLLECTION //deletes all collection
		
		@DeleteMapping(value="/students")
		public String deleteStudents() {
			
		da.deleteStudents();
		
		return "All students are deleted";
		}
		
		//DELETE-ELEMENT //deletes a student
	
		@DeleteMapping(value="/students/{id}", headers={"Content-type=application/json"})
	public String deleteStudentElement(@PathVariable int id) {
			da.deleteStudentElement(id);
			return "Student deleted";
		}

}
