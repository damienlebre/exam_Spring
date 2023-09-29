##################################################################################################################
#####################################exam_Spring##################################################################
##################################################################################################################

*************************************POUR INFOS*******************************************************************




Mon DataCreator créé au lancement du projet des users, (1 admin, 3 journaliste, un user "classique")

  username:                          password:
->  user                                user
    admin                               admin
    Acamus                              user
    Alondres                            user
    Ezola                               user

J'y crée éaglement les roles "admin", "journaliste" et "user"

il crée également les catégories :
    -Sport
    -Faits Divers
    -Politiques
    -Evenements


 et enfin il crée également 5 annonces pour avoir des données test a manipuler

***************************************POUR INFOS BIS**************************************************************

 Niveau authentification, a la base j'étais partie sur un truc probablment un peu trop alamnbiquer je pense...
 j'avais pour intention de me servir de mes differents roles pour set un auteur pour les annonces,
 et pouvoir donner a l'administrateur la possibilité de modifier les roles des autre users, pour "attribuer" 
 le role de journaliste a un user "classique", et pourquoi pas l'ajout de nouvelle categories.

 Pour prendre la main sur la gestion des annonces il faut se connecter avec un compte qui détient
 soit le role "journaliste" soit le role "admin"

  La modification et la suppression des annonce se passe sur la page détail de chaque annonce
