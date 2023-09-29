##################################################################################################################
#####################################exam_Spring##################################################################
##################################################################################################################

*************************************POUR INFOS*******************************************************************




-Mon DataCreator créé au lancement du projet des users, (1 admin, 3 journalistes et 1 user "classique")

  username:                          password:
->  user                                user
    admin                               admin
    Acamus                              user
    Alondres                            user
    Ezola                               user

-J'y crée également les roles "admin", "journaliste" et "user"

-J'y crée également les catégories :
    -Sport
    -Faits Divers
    -Politiques
    -Evenements


 -Et enfin j'y crée également 5 annonces pour avoir des données test à manipuler

***************************************POUR INFOS BIS**************************************************************

 Niveau authentification, à la base j'étais partie sur un truc probablement un peu trop alamnbiqué je pense...
 j'avais pour intention de me servir de mes differents roles pour set un auteur pour les annonces,
 et pouvoir donner à l'administrateur la possibilité de modifier les roles des autre users, pour "attribuer" 
 le role de journaliste a un user "classique" et donc lui autoriser de gerer les annonces, et pourquoi pas 
 l'ajout de nouvelles categories.

 Pour prendre la main sur la gestion des annonces il faut se connecter avec un compte qui détient
 soit le role "journaliste" soit le role "admin"

  La modification et la suppression des annonce se passe sur la page détail de chaque annonce
