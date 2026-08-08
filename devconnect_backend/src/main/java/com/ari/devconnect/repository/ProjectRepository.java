package com.ari.devconnect.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ari.devconnect.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(Long userId);
    
}
// Optional is for queries expected to return 0 or 1 single item.
// List is for queries expected to return 0, 1, or MANY items.   

// By extending JpaRepository, Spring Data JPA automatically inherits dozens of built-in database methods for free!

// You don't have to declare them in your interface—they come built-in from JpaRepository:

// projectRepository.save(project) ➔ Inherited! (Saves/Inserts into DB)
// projectRepository.findById(id) ➔ Inherited! (Finds by primary key)
// projectRepository.findAll() ➔ Inherited! (Fetches all rows)
// projectRepository.delete(project) ➔ Inherited! (Deletes from DB)
// projectRepository.count() ➔ Inherited! (Counts total rows)
