import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiscotecaSimulation {

    private int personeDentro = 0;

    // Metodo sincronizzato per gestire l'ingresso di una persona
    public synchronized void entra() {
        personeDentro++;
        System.out.println(Thread.currentThread().getName() + " è entrata. Persone dentro: " + personeDentro);
    }

    // Metodo sincronizzato per gestire l'uscita di una persona
    public synchronized void esce() {
        personeDentro--;
        System.out.println(Thread.currentThread().getName() + " è uscita. Persone dentro: " + personeDentro);
    }

    // Metodo per ottenere il numero corrente di persone dentro
    public synchronized int getPersoneDentro() {
        return personeDentro;
    }

    public static void main(String[] args) throws InterruptedException {
        DiscotecaSimulation discoteca = new DiscotecaSimulation();
        Random random = new Random();

        // Creiamo un pool di thread per simulare le persone (es: 10 persone)
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Creazione e avvio dei thread (persone)
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    while (true) {
                        // Persona entra in discoteca
                        discoteca.entra();
                        // Rimane all'interno per un tempo casuale (es: tra 1 e 3 secondi)
                        Thread.sleep(random.nextInt(2000) + 1000);
                        // Persona esce dalla discoteca
                        discoteca.esce();
                        // Rimane fuori per un tempo casuale prima di rientrare (es: tra 1 e 2 secondi)
                        Thread.sleep(random.nextInt(1000) + 1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Stampa periodica del numero di persone dentro la discoteca ogni secondo
        while (true) {
            System.out.println("Numero di persone attualmente nella discoteca: " + discoteca.getPersoneDentro());
            Thread.sleep(1000); // Stampa ogni secondo
        }
    }
}
