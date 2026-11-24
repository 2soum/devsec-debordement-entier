package devsec;

// petite boutique : on debite le solde du client du total de la commande
public class Boutique {

    // VULNERABLE : un total negatif (debordement) credite le client
    public static long payerVulnerable(long solde, int prixCentimes, int quantite) {
        int total = CommandeVulnerable.total(prixCentimes, quantite);
        return solde - total;
    }

    // CORRIGE : fail securely, la commande est refusee et le solde ne bouge pas
    public static long payerSecurise(long solde, int prixCentimes, int quantite) {
        try {
            int total = CommandeSecurisee.total(prixCentimes, quantite);
            if (total > solde) {
                return solde;
            }
            return solde - total;
        } catch (ArithmeticException | IllegalArgumentException e) {
            System.out.println("commande refusee : " + e.getMessage());
            return solde;
        }
    }
}
