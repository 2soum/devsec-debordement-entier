package devsec;

public class CommandeSecurisee {

    public static final int QUANTITE_MAX = 1000;

    // CORRIGE : validation metier + multiplyExact qui leve une exception au lieu de deborder
    public static int total(int prixCentimes, int quantite) {
        if (prixCentimes < 0 || quantite < 1 || quantite > QUANTITE_MAX) {
            throw new IllegalArgumentException("commande invalide");
        }
        return Math.multiplyExact(prixCentimes, quantite);
    }

    // CORRIGE : comparaison reecrite sans l'addition qui deborde
    public static boolean accesAutorise(int offset, int longueur, int taille) {
        if (offset < 0 || longueur < 0 || offset > taille) {
            return false;
        }
        return longueur <= taille - offset;
    }

    // CORRIGE : toIntExact leve une exception si la valeur ne tient pas dans un int
    public static int versInt(long valeur) {
        return Math.toIntExact(valeur);
    }
}
