# UltraPlanning

## Compilation
(Toutes les informations qui suivent provienne de ce [lien](https://stackoverflow.com/questions/32747917/intellij-gui-designer-maven-executable-jar-export))

Les dépendances javac2, forms_rt et asm-all peuvent être manquantes sur maven.
Voici les commandes à exécuter pour les installer :


ATTENTION : Il se peut que la dernière ligne provoque une erreur car la dépendance asm-all est introuvable.
Il suffit alors, dans le dossier /lib du répertoire d'Intellij, faire une copie du fichier asm-all-[VERSION].jar et de renommer cette copie en asm-all.jar

### Linux
```shell
INTELLIJ_HOME=[RÉPERRTOIRE DE INTELLIJ]
mvn install:install-file -Dfile="$INTELLIJ_HOME/lib/javac2.jar" -DgroupId=com.intellij -DartifactId=javac2 -Dversion=17.1.5 -Dpackaging=jar
mvn install:install-file -Dfile="$INTELLIJ_HOME/lib/forms_rt.jar" -DgroupId=com.intellij -DartifactId=forms_rt -Dversion=17.1.5 -Dpackaging=jar
mvn install:install-file -Dfile="$INTELLIJ_HOME/lib/asm-all.jar" -DgroupId=com.intellij -DartifactId=asm-all -Dversion=17.1.5 -Dpackaging=jar
```

### Windows
```powershell
INTELLIJ_HOME=[RÉPERRTOIRE DE INTELLIJ]
mvn install:install-file -Dfile="%INTELLIJ_HOME%\lib\javac2.jar" -DgroupId=com.intellij -DartifactId=javac2 -Dversion=17.1.5 -Dpackaging=jar
mvn install:install-file -Dfile="%INTELLIJ_HOME%\lib\forms_rt.jar" -DgroupId=com.intellij -DartifactId=forms_rt -Dversion=17.1.5 -Dpackaging=jar
mvn install:install-file -Dfile="%INTELLIJ_HOME%\lib\asm-all.jar" -DgroupId=com.intellij -DartifactId=asm-all -Dversion=17.1.5 -Dpackaging=jar
```

