# Réponses — TP 2 : Carnet de contacts

## Q1. Pourquoi la colonne user_id est-elle indispensable pour respecter la règle « un utilisateur ne voit que ses contacts » ?

La colonne `user_id` relie chaque contact à son propriétaire grâce à une clé étrangère vers la table `users`. Elle permet de filtrer les contacts selon l'identifiant de l'utilisateur connecté. Ainsi, chaque utilisateur ne peut consulter que ses propres contacts.

## Q2. Que se passerait-il, sans `ON DELETE CASCADE`, si on supprimait un utilisateur qui a des contacts ?

Sans `ON DELETE CASCADE`, la suppression de l'utilisateur pourrait être refusée par MySQL si des contacts lui sont encore associés, à cause de la clé étrangère. Il faudrait alors supprimer manuellement ses contacts avant de supprimer son compte.

## Q3. Pourquoi `update` et `delete` doivent-ils aussi vérifier `user_id` dans leur `WHERE`, alors que l'`id` du contact suffit à l'identifier ?

L'identifiant du contact permet de retrouver une ligne, mais il ne prouve pas que l'utilisateur connecté en est propriétaire. Ajouter `user_id` dans le `WHERE` garantit qu'un utilisateur ne peut modifier ou supprimer que ses propres contacts, même s'il connaît l'identifiant d'un contact appartenant à quelqu'un d'autre.
