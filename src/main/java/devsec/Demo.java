package devsec;

// affiche les resultats bruts (mvn compile exec ou java -cp target/classes devsec.Demo)
public class Demo {

    public static void main(String[] args) {
        System.out.println("Integer.MAX_VALUE + 1        = " + (Integer.MAX_VALUE + 1));
        System.out.println("1_000_000 * 1_000_000        = " + (1_000_000 * 1_000_000));
        System.out.println("Math.abs(Integer.MIN_VALUE)  = " + Math.abs(Integer.MIN_VALUE));
        System.out.println("(int) 3_000_000_000L         = " + (int) 3_000_000_000L);

        int low = 1_500_000_000, high = 2_000_000_000;
        System.out.println("(low + high) / 2             = " + (low + high) / 2);
        System.out.println("low + (high - low) / 2       = " + (low + (high - low) / 2));
        System.out.println("(low + high) >>> 1           = " + ((low + high) >>> 1));

        System.out.println();
        System.out.println("total 1500 cts x 2_000_000   = " + CommandeVulnerable.total(1500, 2_000_000));
        System.out.println("solde 100 EUR apres paiement = "
                + Boutique.payerVulnerable(10_000, 1500, 2_000_000) + " cts");
        System.out.println("acces offset MAX-5, long 100 = "
                + CommandeVulnerable.accesAutorise(Integer.MAX_VALUE - 5, 100, 1024));

        System.out.println();
        System.out.println("version corrigee :");
        System.out.println("solde apres paiement         = "
                + Boutique.payerSecurise(10_000, 1500, 2_000_000) + " cts");
        System.out.println("acces offset MAX-5, long 100 = "
                + CommandeSecurisee.accesAutorise(Integer.MAX_VALUE - 5, 100, 1024));
        try {
            Math.multiplyExact(1500, 2_000_000);
        } catch (ArithmeticException e) {
            System.out.println("multiplyExact                -> ArithmeticException: " + e.getMessage());
        }
    }
}
