package org.yash.quize_app.service;

import org.springframework.stereotype.Service;
import org.yash.quize_app.entity.Subject;
import org.yash.quize_app.repository.SubjectRepository;

import java.util.List;

@Service
public class SubjectService {

     SubjectRepository subjectRepository;

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubject(){
        return subjectRepository.findAll();
    }

    public void deleteSubject(Long id){
        subjectRepository.deleteById(id);
    }
}
