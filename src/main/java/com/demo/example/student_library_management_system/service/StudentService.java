package com.demo.example.student_library_management_system.service;

import com.demo.example.student_library_management_system.converter.StudentConverter;
import com.demo.example.student_library_management_system.enums.CardStatus;
import com.demo.example.student_library_management_system.model.Card;
import com.demo.example.student_library_management_system.model.Student;
import com.demo.example.student_library_management_system.repository.StudentRepository;
import com.demo.example.student_library_management_system.requestdto.StudentRequestDto;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String addStudent(StudentRequestDto studentRequestDto){

        // first convert the studentRequestDto into student model class
        Student student = StudentConverter.convertStudentRequestDtoIntoStudent(studentRequestDto);

        // whenever the student is created card also automatically gets created
        Card card = new Card();
        card.setCardStatus(CardStatus.ACTIVE);
        student.setCard(card);

        card.setStudent(student);

        studentRepository.save(student);
        return "Student and Card saved successfully!";

    }

    public Student getByStudentId(int id){
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent()){
            return studentOptional.get();
        }else{
            throw new RuntimeException("Student not found : "+id);
        }
    }

    public List<Student> getAllStudents(){
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    /*
    pagination - fetching or getting the records or data in the form of pages
    pagenumber - the number of page we want to see(0,1,2,3,4,5,....)
    pagesize - total number of records in each page(fixed for all pages)

    total number of records - 28, page size - 5
    0th page - 1-5
    1st page - 6-10
    2nd page - 11-15
    3rd page - 16-20
    4th page - 21-25
    5th page - 26-28
    6th page - 0

     total number of records - 11, page size - 3
     0th page -1-3
     1st page - 4-6
     2nd page - 7-9
     3rd page - 10-11

     sorting - arranging the record based on ascending or descending order

     only pagination
     public List<Student> getAllStudentsByPage(int pageNo, int pageSize){
       List<Student> studentList = studentRepository.findAll(PageRequest.of(pageNo, pageSize)).getContent();
       return studentList;
    }

     */

    // pagination and sorting together
    public List<Student> getAllStudentsByPage(int pageNo, int pageSize){
       List<Student> studentList = studentRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by("name").ascending())).getContent();
       return studentList;
    }

    public String deletedStudentById(int id){
        studentRepository.deleteById(id);
        return "Student with id : "+id+" got deleted along with it's card";
    }

    public String updateStudent(int id, StudentRequestDto studentRequestDto){
        // check whether student is present with that id or not

        Student student = getByStudentId(id);
        if(student!=null){
            student.setName(studentRequestDto.getName());
            student.setMobile(studentRequestDto.getMobile());
            student.setDob(studentRequestDto.getDob());
            student.setSem(studentRequestDto.getSem());
            student.setEmail(studentRequestDto.getEmail());
            student.setDept(studentRequestDto.getDept());
            student.setGender(studentRequestDto.getGender());

            studentRepository.save(student);
            return "Student updated successfully";
        }else{
            return "Student not found, we cannot update";
        }


    }
}
