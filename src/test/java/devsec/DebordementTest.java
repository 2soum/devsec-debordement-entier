package devsec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DebordementTest {

    // 15 EUR x 2 millions = 3 milliards de centimes, ca ne tient pas dans un int
    static final int PRIX = 1500;
    static final int QUANTITE_ATTAQUE = 2_000_000;
    static final long SOLDE = 10_000; // 100 EUR

    // --- avant : la faille existe ---

    @Test
    void vulnerable_total_devient_negatif() {
        assertEquals(-1_294_967_296, CommandeVulnerable.total(PRIX, QUANTITE_ATTAQUE));
    }

    @Test
    void vulnerable_le_client_est_credite() {
        long solde = Boutique.payerVulnerable(SOLDE, PRIX, QUANTITE_ATTAQUE);
        assertTrue(solde > SOLDE); // il a gagne de l'argent en "payant"
    }

    @Test
    void vulnerable_controle_de_bornes_contourne() {
        // offset + longueur = -2 147 483 554, donc "<= 1024"
        assertTrue(CommandeVulnerable.accesAutorise(Integer.MAX_VALUE - 5, 100, 1024));
    }

    @Test
    void vulnerable_cast_tronque() {
        assertEquals(-1_294_967_296, CommandeVulnerable.versInt(3_000_000_000L));
    }

    // --- apres : les memes entrees sont bloquees ---

    @Test
    void securise_total_refuse() {
        assertThrows(IllegalArgumentException.class,
                () -> CommandeSecurisee.total(PRIX, QUANTITE_ATTAQUE));
    }

    @Test
    void securise_multiplyExact_detecte_meme_sans_validation() {
        assertThrows(ArithmeticException.class,
                () -> CommandeSecurisee.total(Integer.MAX_VALUE, 2));
    }

    @Test
    void securise_solde_inchange() {
        assertEquals(SOLDE, Boutique.payerSecurise(SOLDE, PRIX, QUANTITE_ATTAQUE));
    }

    @Test
    void securise_controle_de_bornes_tient() {
        assertFalse(CommandeSecurisee.accesAutorise(Integer.MAX_VALUE - 5, 100, 1024));
    }

    @Test
    void securise_cast_leve_exception() {
        assertThrows(ArithmeticException.class, () -> CommandeSecurisee.versInt(3_000_000_000L));
    }

    // --- et le fonctionnement normal marche toujours ---

    @Test
    void securise_commande_normale_ok() {
        assertEquals(4500, CommandeSecurisee.total(PRIX, 3));
        assertEquals(5500, Boutique.payerSecurise(SOLDE, PRIX, 3));
    }

    @Test
    void securise_bornes_limites_ok() {
        assertTrue(CommandeSecurisee.accesAutorise(0, 1024, 1024));
        assertTrue(CommandeSecurisee.accesAutorise(1000, 24, 1024));
        assertFalse(CommandeSecurisee.accesAutorise(1000, 25, 1024));
    }

    // --- pieges : ce qui ne se corrige pas tout seul ---

    @Test
    void piege_abs_min_value_reste_negatif() {
        assertTrue(Math.abs(Integer.MIN_VALUE) < 0);
        assertThrows(ArithmeticException.class, () -> Math.absExact(Integer.MIN_VALUE));
    }

    @Test
    void piege_milieu_recherche_dichotomique() {
        int low = 1_500_000_000, high = 2_000_000_000;
        assertTrue((low + high) / 2 < 0);
        assertEquals(1_750_000_000, low + (high - low) / 2);
        assertEquals(1_750_000_000, (low + high) >>> 1);
    }
}
