package com.dlebre.exam_Spring;

import com.dlebre.exam_Spring.models.Annonce;
import com.dlebre.exam_Spring.models.Category;
import com.dlebre.exam_Spring.models.Role;
import com.dlebre.exam_Spring.models.User;
import com.dlebre.exam_Spring.services.AnnonceService;
import com.dlebre.exam_Spring.services.CategoryService;
import com.dlebre.exam_Spring.services.RoleService;
import com.dlebre.exam_Spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;


@SpringBootApplication
public class DataCreatorApplication {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(DataCreatorApplication.class);

    }

    @Bean
    public CommandLineRunner dataLoader(UserService userService, RoleService roleService, CategoryService categoryService, AnnonceService annonceService) {
        return args -> {
            // Création des Roles
            Role roleAdmin = roleService.findByName("admin");
            Role roleUser = roleService.findByName("user");
            Role roleJournaliste = roleService.findByName("journaliste");

            if (roleAdmin == null) {
                roleAdmin = new Role("admin");
                roleService.saveRole(roleAdmin);
            }

            if (roleUser == null) {
                roleUser = new Role("user");
                roleService.saveRole(roleUser);
            }

            if (roleJournaliste == null) {
                roleJournaliste = new Role("journaliste");
                roleService.saveRole(roleJournaliste);
            }

            // Création des users
            if (userService.findByUsername("user") == null) {
                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setFirstname("User");
                user.setLastname("User");

                user.addRole(roleUser);

                userService.registerUser(user);
            }

            if (userService.findByUsername("Acamus") == null) {
                User user = new User();
                user.setUsername("Acamus");
                user.setPassword("user");
                user.setFirstname("Albert");
                user.setLastname("Camus");

                user.addRole(roleJournaliste);

                userService.registerUser(user);
            }
            if (userService.findByUsername("Ezola") == null) {
                User user = new User();
                user.setUsername("Ezola");
                user.setPassword("user");
                user.setFirstname("Emile");
                user.setLastname("Zola");

                user.addRole(roleJournaliste);

                userService.registerUser(user);
            }

            if (userService.findByUsername("Alondres") == null) {
                User user = new User();
                user.setUsername("Alondres");
                user.setPassword("user");
                user.setFirstname("Albert");
                user.setLastname("Londres");

                user.addRole(roleJournaliste);

                userService.registerUser(user);
            }

            if (userService.findByUsername("Admin") == null) {
                User user = new User();
                user.setUsername("Admin");
                user.setPassword("admin");
                user.setFirstname("Admin");
                user.setLastname("Admin");

                user.addRole(roleUser);
                user.addRole(roleAdmin);

                userService.registerUser(user);
            }
            // Création des catégories (Sport, Faits divers, Politique, Evenements)
            if (categoryService.getCategoryByName("Sport") == null) {
                Category category1 = new Category();
                category1.setName("Sport");

                categoryService.addCategory(category1);
            }

            if (categoryService.getCategoryByName("Faits divers") == null) {
                Category category2 = new Category();
                category2.setName("Faits divers");

                categoryService.addCategory(category2);
            }

            if (categoryService.getCategoryByName("Politique") == null) {
                Category category3 = new Category();
                category3.setName("Politique");

                categoryService.addCategory(category3);
            }

            if (categoryService.getCategoryByName("Evenements") == null) {
                Category category4 = new Category();
                category4.setName("Evenements");

                categoryService.addCategory(category4);
            }

            // Crée une annonce avec la catégorie "Sport" et le journaliste "Acamus"
            if (annonceService.getAnnonceByTitle("Annonce 1") == null) {
                Annonce annonce1 = new Annonce();
                annonce1.setTitle("Annonce 1");
                annonce1.setImage("/upload/1.png");
                annonce1.setContent("Contenu de l'annonce 1");
                annonce1.setPublicationDate(new Date());
                annonce1.setCategory(categoryService.getCategoryByName("Sport"));
//                annonce1.setJournaliste(userService.findByUsername("Acamus"));
                annonceService.addAnnonce(annonce1);
            }

// Crée une annonce avec la catégorie "Faits divers" et le journaliste "Ezola"
            if (annonceService.getAnnonceByTitle("Annonce 2") == null) {
                Annonce annonce2 = new Annonce();
                annonce2.setTitle("Annonce 2");
                annonce2.setImage("/upload/2.jpg");
                annonce2.setContent("Contenu de l'annonce 2");
                annonce2.setPublicationDate(new Date());
                annonce2.setCategory(categoryService.getCategoryByName("Faits divers"));
//                annonce2.setJournaliste(userService.findByUsername("Ezola"));
                annonceService.addAnnonce(annonce2);
            }

// Crée une annonce avec la catégorie "Politique" et l'admin "Admin"
            if (annonceService.getAnnonceByTitle("Annonce 3") == null) {
                Annonce annonce3 = new Annonce();
                annonce3.setTitle("Annonce 3");
                annonce3.setImage("/upload/3.png");
                annonce3.setContent("Contenu de l'annonce 3");
                annonce3.setPublicationDate(new Date());
                annonce3.setCategory(categoryService.getCategoryByName("Politique"));
//                annonce3.setJournaliste(userService.findByUsername("Admin"));
                annonceService.addAnnonce(annonce3);
            }

// Crée une annonce avec la catégorie "Evenements" et le journaliste "Acamus"
            if (annonceService.getAnnonceByTitle("Annonce 4") == null) {
                Annonce annonce4 = new Annonce();
                annonce4.setTitle("Annonce 4");
                annonce4.setImage("/upload/1.png");
                annonce4.setContent("Contenu de l'annonce 4");
                annonce4.setPublicationDate(new Date());
                annonce4.setCategory(categoryService.getCategoryByName("Evenements"));
//                annonce4.setJournaliste(userService.findByUsername("Acamus"));
                annonceService.addAnnonce(annonce4);
            }

// Crée une annonce avec la catégorie "Sport" et le journaliste "Ezola"
            if (annonceService.getAnnonceByTitle("Annonce 5") == null) {
                Annonce annonce5 = new Annonce();
                annonce5.setTitle("Annonce 5");
                annonce5.setImage("/upload/3.png");
                annonce5.setContent("Contenu de l'annonce 5");
                annonce5.setPublicationDate(new Date());
                annonce5.setCategory(categoryService.getCategoryByName("Sport"));
//                annonce5.setJournaliste(userService.findByUsername("Ezola"));
                annonceService.addAnnonce(annonce5);
            }



        };

    }
}