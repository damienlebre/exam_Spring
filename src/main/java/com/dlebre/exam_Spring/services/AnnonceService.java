package com.dlebre.exam_Spring.services;

import com.dlebre.exam_Spring.models.Annonce;
import com.dlebre.exam_Spring.models.Category;
import com.dlebre.exam_Spring.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private CategoryService categoryService;

    public List<Annonce> getAllAnnonces() {
        return (List<Annonce>) annonceRepository.findAll();
    }

    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    public Annonce getAnnonceByTitle(String title) {
        return annonceRepository.findByTitle(title);
    }

    public void addAnnonce(Annonce annonce) {
        annonceRepository.save(annonce);
    }

    public void updateAnnonce(Annonce annonce) {
        annonceRepository.save(annonce);
    }

    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }

    public List<Annonce> getAnnoncesByCategoryId(Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            return getAllAnnonces().stream()
                    .filter(annonce -> annonce.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        return null;
    }
}