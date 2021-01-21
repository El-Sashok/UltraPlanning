# UltraPlanning

## Compilation
(Toutes les informations qui suivent proviennent de ce [lien](https://stackoverflow.com/questions/32747917/intellij-gui-designer-maven-executable-jar-export))

Les dépendances javac2, forms_rt et asm-all peuvent être manquantes sur maven.
Voici les commandes à exécuter pour les installer :

__ATTENTION__ : Suivant les versions d’intellij, il se peut que la dernière ligne provoque une erreur car la dépendance asm-all-9.0 est introuvable.
Il suffit alors de changer le nom du fichier en prenant en compte votre version d’asm-all

### Linux
```shell
INTELLIJ_HOME=[RACINE DU RÉPERRTOIRE D’INTELLIJ]
mvn install:install-file -Dfile="$INTELLIJ_HOME/lib/javac2.jar" -DgroupId=com.intellij -DartifactId=javac2 -Dversion=17.1.5 -Dpackaging=jar
mvn install:install-file -Dfile="$INTELLIJ_HOME/lib/forms_rt.jar" -DgroupId=com.intellij -DartifactId=forms_rt -Dversion=17.1.5 -Dpackaging=jar
mvn install:install-file -Dfile="$INTELLIJ_HOME/lib/asm-all-9.0.jar" -DgroupId=com.intellij -DartifactId=asm-all -Dversion=17.1.5 -Dpackaging=jar
```

### Windows (Powershell)
```powershell
$env:INTELLIJ_HOME=[RACINE DU RÉPERRTOIRE D’INTELLIJ]
mvn install:install-file -Dfile="$env:INTELLIJ_HOME\lib\javac2.jar" -DgroupId="com.intellij" -DartifactId="asm-all" -Dversion="17.1.5" -Dpackaging="jar"
mvn install:install-file -Dfile="$env:INTELLIJ_HOME\lib\forms_rt.jar" -DgroupId="com.intellij" -DartifactId="asm-all" -Dversion="17.1.5" -Dpackaging="jar"
mvn install:install-file -Dfile="$env:INTELLIJ_HOME\lib\asm-all-9.0.jar" -DgroupId="com.intellij" -DartifactId="asm-all" -Dversion="17.1.5" -Dpackaging="jar"
```

### Compilation du projet

Placez-vous à la racine du projet et tapez la commande suivante
```
mvn clean compile assembly:single
```

## Execution

Vous pouvez télécharger le fichier executable [ici](https://github.com/El-Sashok/UltraPlanning/releases/tag/1.0)

__Attention__ : Pensez à placer le fichier database.mv.db à côté du fichier UltraPlanning.jar avant l'exécution.

Double-cliquez sur UltraPlanning.jar pour lancer le programme. Ou alors exécutez la commande suivante :
```
java -jar UltraPlanning.jar
```

## Démo

La database fournie pour la démo contient déjà 4 comptes utilisateurs et des cours remplis.

Les comptes sont student (mot de passe: passe), teacher (mot de passe: test), manager (mot de passe: test) et admin (mot de passe admin)

Pour accéder à la base de donnée de manière tierce, le nom d'utilsateur est root et le mot de passe toor.
