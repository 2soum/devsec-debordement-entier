package devsec;

public class CommandeVulnerable {

    // VULNERABLE : prix * quantite en int, si ca depasse ca fait le tour sans erreur
    public static int total(int prixCentimes, int quantite) {
        return prixCentimes * quantite;
    }

    // VULNERABLE : le controle de bornes deborde lui-meme
    // offset + longueur peut devenir negatif et passer le test
    public static boolean accesAutorise(int offset, int longueur, int taille) {
        if (offset < 0 || longueur < 0) {
            return false;
        }
        return offset + longueur <= taille;
    }

    // VULNERABLE : conversion long -> int qui tronque en silence
    public static int versInt(long valeur) {
        return (int) valeur;
    }
}
