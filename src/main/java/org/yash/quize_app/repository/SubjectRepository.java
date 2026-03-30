package org.yash.quize_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yash.quize_app.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
}
