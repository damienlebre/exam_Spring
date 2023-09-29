package com.dlebre.exam_Spring.repository;

import com.dlebre.exam_Spring.models.Annonce;
import com.dlebre.exam_Spring.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name);

}
