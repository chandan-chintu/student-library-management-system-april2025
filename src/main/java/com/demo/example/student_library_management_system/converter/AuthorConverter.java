package com.demo.example.student_library_management_system.converter;

import com.demo.example.student_library_management_system.model.Author;
import com.demo.example.student_library_management_system.model.Student;
import com.demo.example.student_library_management_system.requestdto.AuthorRequestDto;
import com.demo.example.student_library_management_system.requestdto.StudentRequestDto;

public class AuthorConverter {

    public static Author convertAuthorRequestDtoIntoAuthor(AuthorRequestDto authorRequestDto){
        Author author = new Author();

        author.setName(authorRequestDto.getName());
        author.setCountry(authorRequestDto.getCountry());
        author.setGender(authorRequestDto.getGender());
        author.setRating(authorRequestDto.getRating());
        author.setEmail(authorRequestDto.getEmail());

        return author;

    }
}
