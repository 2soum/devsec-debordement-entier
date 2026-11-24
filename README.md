# Debordement d'entier - DevSec ALTN96

Demo d'un debordement d'entier en Java (CWE-190, CERT NUM00-J) et de sa correction.

- `src/main/java/devsec/CommandeVulnerable.java` : total `prix * quantite`, controle `offset + longueur <= taille` et cast `(int)` (vulnerables)
- `src/main/java/devsec/CommandeSecurisee.java` : validation + `Math.multiplyExact`, comparaison reecrite, `Math.toIntExact` (corriges)
- `src/main/java/devsec/Boutique.java` : le debordement en situation (paiement qui credite le client)
- `src/test/java/devsec/DebordementTest.java` : les memes entrees sur les deux versions + pieges
- `src/main/java/devsec/Demo.java` : affiche les valeurs brutes
- `python/commande.py` : le meme calcul en Python
- `c/controle.c` : le controle `x + 100 < x` supprime par le compilateur en -O2

## Lancer

```
mvn test
mvn -q compile && java -cp target/classes devsec.Demo
python3 python/commande.py
cc -O0 c/controle.c && ./a.out
cc -O2 c/controle.c && ./a.out
```

JDK 25, Maven, JUnit 5. Python 3 (numpy optionnel). Apple clang 17.
