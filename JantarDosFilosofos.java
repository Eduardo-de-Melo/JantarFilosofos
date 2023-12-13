import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Classe que representa um filósofo
class Filosofo implements Runnable {
    private final int id;
    private final Lock garfoEsquerda;
    private final Lock garfoDireita;

    //Construtor
    public Filosofo(int id, Lock garfoEsquerda, Lock garfoDireita) {
        this.id = id;
        this.garfoEsquerda = garfoEsquerda;
        this.garfoDireita = garfoDireita;
    }

    //Método para pegar os garfos
    private void pegarGarfos() {
        System.out.println("Filósofo " + id + " está tentando pegar os garfos.");
        garfoEsquerda.lock();  // Adquire o lock do garfo à esquerda
        garfoDireita.lock();   //Adquire o lock do garfo à direita
        System.out.println("Filósofo " + id + " pegou os garfos e está comendo.");
    }

    //Método para soltar os garfos
    private void soltarGarfos() {
        System.out.println("Filósofo " + id + " terminou de comer e está soltando os garfos.");
        garfoEsquerda.unlock();  //Libera o garfo à esquerda
        garfoDireita.unlock();   //Libera o garfo à direita
    }

    //Método para o comportamento de cada filósofo
    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                pegarGarfos();
                comer();
                soltarGarfos();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //Método para simular o filósofo pensando
    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando.");
        Thread.sleep(1000);  //Simula o pensamento(1 seg)
    }

    //Método simular o filósofo comendo
    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está comendo.");
        Thread.sleep(1000);  //Simula o ato de comer(1 seg)
    }
}
public class JantarDosFilosofos {
    public static void main(String[] args) {
        int numFilosofos = 5;
        Filosofo[] filosofos = new Filosofo[numFilosofos];
        Lock[] garfos = new ReentrantLock[numFilosofos];

        //Inicialização dos locks (garfos)
        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new ReentrantLock();
        }

        //Criação e inicialização dos filósofos
        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, garfos[i], garfos[(i + 1) % numFilosofos]);
            new Thread(filosofos[i]).start();  //Inicia a execução da thread do filósofo
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        //Interrompe os filósofos e encerra o programa
        System.out.println("Encerrando o jantar.");
        System.exit(0);
    }
}