public interface Interface {
    double calculTotal();
    public static double calculeazaPenalizare(double suma, long zileIntarziere) {
        return suma * 0.001 * zileIntarziere;
    }
}