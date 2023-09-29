package com.dlebre.exam_Spring.repository;

import com.dlebre.exam_Spring.models.Annonce;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends CrudRepository<Annonce, Long> {

    Annonce findByTitle(String title);

}
