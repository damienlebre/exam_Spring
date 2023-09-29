package com.dlebre.exam_Spring.controllers;

import com.dlebre.exam_Spring.exception.WrongFileTypeException;
import com.dlebre.exam_Spring.models.Annonce;
import com.dlebre.exam_Spring.models.Category;
import com.dlebre.exam_Spring.services.AnnonceService;
import com.dlebre.exam_Spring.services.CategoryService;
import com.dlebre.exam_Spring.services.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/annonces")
public class AnnonceController {

    @Autowired
    AnnonceService annonceService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(name = "categoryFilter", required = false) Long categoryFilter) {
        ModelAndView mv = new ModelAndView("annonces/list");

        List<Annonce> annonces;

        if (categoryFilter != null) {
            // Filtrer annonces par catégorie
            annonces = annonceService.getAnnoncesByCategoryId(categoryFilter);
        } else {
            // Sinon-> récupérer liste toutes les annonces
            annonces = annonceService.getAllAnnonces();
        }

        // Récupérer liste catégories
        List<Category> categories = categoryService.getAllCategories();

        mv.addObject("annonces", annonces);
        mv.addObject("categories", categories);

        // Ajouter  valeur categoryFilter au model
        mv.addObject("categoryId", categoryFilter);

        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewAnnonce(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("annonces/detail");
        Optional<Annonce> annonceOptional = annonceService.getAnnonceById(id);
        Annonce annonce = annonceOptional.orElse(null);
        mv.addObject("annonce", annonce);
        return mv;
    }


    @RequestMapping(value = "/ajouter", method = RequestMethod.GET)
    public ModelAndView showAddForm(Principal principal) {
        // Vérifie le rôle
        boolean isJournalisteOrAdmin = principal != null &&
                (principal instanceof Authentication &&
                        (((Authentication) principal).getAuthorities().stream()
                                .anyMatch(grantedAuthority ->
                                        grantedAuthority.getAuthority().equals("journaliste") ||
                                                grantedAuthority.getAuthority().equals("admin"))));

        // Si journaliste ou admin -> autorise accès page d'ajout
        if (isJournalisteOrAdmin) {
            ModelAndView mv = new ModelAndView("annonces/ajouter");
            mv.addObject("annonce", new Annonce());

            // Ajout liste  catégories
            List<Category> categories = categoryService.getAllCategories();
            mv.addObject("categories", categories);

            return mv;
        } else {
            // Sinon-> redirection vers liste
            return new ModelAndView("redirect:/annonces/list");
        }
    }

    @RequestMapping(value = "/ajouter", method = RequestMethod.POST)
    public String addAnnonce(@ModelAttribute("annonce") @Valid Annonce annonce,
                             Principal principal, BindingResult bindingResult,
                             @RequestParam("imageAnnonce") MultipartFile imageAnnonce, Model model) throws IOException {
        if (annonce.getPublicationDate() == null) {
            annonce = new Annonce();
        }

        if (bindingResult.hasErrors()) {
            return "annonce/ajouter";
        } else {
            try {
                annonce.setImage(storageService.store(imageAnnonce));

                this.annonceService.addAnnonce(annonce);
                return "redirect:/annonces/list";
            } catch (WrongFileTypeException e) {
                model.addAttribute("uploadError", "Veuillez ajouter une image");
                return "annonces/ajouter";
            }
        }
    }


    @RequestMapping(value = "/modifier/{id}", method = RequestMethod.GET)
    public ModelAndView showEditForm(@PathVariable Long id, Principal principal) {
        // Vérifier rôle utilisateur
        boolean isJournalisteOrAdmin = principal != null &&
                (principal instanceof Authentication &&
                        (((Authentication) principal).getAuthorities().stream()
                                .anyMatch(grantedAuthority ->
                                        grantedAuthority.getAuthority().equals("journaliste") ||
                                                grantedAuthority.getAuthority().equals("admin"))));

        // Si journaliste ou admin -> autorise accès  page  modification
        if (isJournalisteOrAdmin) {
            ModelAndView mv = new ModelAndView("annonces/modifier");
            Optional<Annonce> annonceOptional = annonceService.getAnnonceById(id);
            Annonce annonce = annonceOptional.orElse(null);
            mv.addObject("annonce", annonce);

            // Ajouter  liste catégories au modèle
            List<Category> categories = categoryService.getAllCategories();
            mv.addObject("categories", categories);

            return mv;
        } else {
            // Sinon-> redirection vers la liste ou une page d'erreur d'accès refusé
            return new ModelAndView("redirect:/annonces/list");
        }
    }

    @RequestMapping(value = "/modifier/{id}", method = RequestMethod.POST)
    public ModelAndView editAnnonce(@PathVariable Long id, @ModelAttribute("annonce") Annonce annonce, @RequestParam("imageAnnonce") MultipartFile imageAnnonce, Principal principal) {
        // Récupérer l'annonce existante de la base de données
        Optional<Annonce> existingAnnonceOptional = annonceService.getAnnonceById(id);
        if (existingAnnonceOptional.isPresent()) {
            Annonce existingAnnonce = existingAnnonceOptional.get();

            // Vérifier si une nouvelle image a été téléchargée
            if (!imageAnnonce.isEmpty()) {
                // Si une nouvelle image a été téléchargée, mettez à jour l'image de l'annonce
                try {
                    existingAnnonce.setImage(storageService.store(imageAnnonce));
                } catch (WrongFileTypeException | IOException e) {
                    // Gérez l'exception si le type de fichier est incorrect

                }
            }

            existingAnnonce.setTitle(annonce.getTitle());
            existingAnnonce.setContent(annonce.getContent());
            existingAnnonce.setPublicationDate(new Date());
            existingAnnonce.setCategory(annonce.getCategory());

            annonceService.updateAnnonce(existingAnnonce);

            ModelAndView mv = new ModelAndView("redirect:/annonces/list");
            return mv;
        }

        // Si l'annonce n'existe pas -> redirection vers  liste
        return new ModelAndView("redirect:/annonces/list");
    }


    @RequestMapping(value = "/supprimer/{id}", method = RequestMethod.GET)
    public ModelAndView deleteAnnonce(@PathVariable Long id, Principal principal) {
        // Vérifier rôle utilisateur pour sécurité
        boolean isJournalisteOrAdmin = principal != null &&
                (principal instanceof Authentication &&
                        (((Authentication) principal).getAuthorities().stream()
                                .anyMatch(grantedAuthority ->
                                        grantedAuthority.getAuthority().equals("journaliste") ||
                                                grantedAuthority.getAuthority().equals("admin"))));

        // Si journaliste ou admin, autorise la suppression de l'annonce
        if (isJournalisteOrAdmin) {
            // Vérifie si l'annonce existe
            Optional<Annonce> annonceOptional = annonceService.getAnnonceById(id);
            if (annonceOptional.isPresent()) {
                // Supprimez l'annonce de la base de données
                annonceService.deleteAnnonce(id);

                ModelAndView mv = new ModelAndView("redirect:/annonces/list");
                return mv;
            } else {
                // Sinon, redirection vers la liste
                return new ModelAndView("redirect:/annonces/list");
            }
        }

        // Sinon, redirection vers la liste
        return new ModelAndView("redirect:/annonces/list");
    }





}

