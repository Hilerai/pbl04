package src;

import java.io.IOException;

public class TesteOrdenacao {

    // LINHA TABELA
    static String[] linhasTabela = new String[200];
    static int indiceTabela = 0;

    public static void main(String[] args) {

        String[] tipos = {"Aleatório", "Crescente", "Decrescente"};

        String[][] arquivos = {
            {"data/aleatorio_100.csv", "data/aleatorio_1000.csv", "data/aleatorio_10000.csv"},
            {"data/crescente_100.csv", "data/crescente_1000.csv", "data/crescente_10000.csv"},
            {"data/decrescente_100.csv", "data/decrescente_1000.csv", "data/decrescente_10000.csv"}
        };

        System.out.println("\nExecutando testes de ordenação...\n");

        try {
            for (int t = 0; t < 3; t++) {
                for (int a = 0; a < 3; a++) {

                    String tipo = tipos[t];
                    String caminho = arquivos[t][a];

                    int[] dados = LeitorCsvSimples.lerArquivo(caminho);
                    int n = dados.length;

                    registrarResultado("Bubble Sort", tipo, n, medirTempoBubble(dados));
                    registrarResultado("Insertion Sort", tipo, n, medirTempoInsertion(dados));
                    registrarResultado("Quick Sort", tipo, n, medirTempoQuick(dados));
                }
            }

            imprimirTabela();

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    // REGISTRO E TABELA

    private static void registrarResultado(String algoritmo, String tipo, int tamanho, double tempoMs) {
        String linha = String.format("%-15s | %-12s | %-7d | %-10.4f",
                algoritmo, tipo, tamanho, tempoMs);

        linhasTabela[indiceTabela] = linha;
        indiceTabela++;
    }

    private static void imprimirTabela() {
        System.out.println("\n---------------------------------------------------------------");
        System.out.println("Algoritmo       | Tipo         | Tamanho | Tempo (ms)");
        System.out.println("---------------------------------------------------------------");

        for (int i = 0; i < indiceTabela; i++) {
            System.out.println(linhasTabela[i]);
        }

        System.out.println("---------------------------------------------------------------\n");
    }

    // MEDICAO

    private static double medirTempoBubble(int[] dados) {
        int[] copia = copiar(dados);
        long inicio = System.nanoTime();
        bubbleSort(copia);
        long fim = System.nanoTime();
        return (fim - inicio) / 1_000_000.0;
    }

    private static double medirTempoInsertion(int[] dados) {
        int[] copia = copiar(dados);
        long inicio = System.nanoTime();
        insertionSort(copia);
        long fim = System.nanoTime();
        return (fim - inicio) / 1_000_000.0;
    }

    private static double medirTempoQuick(int[] dados) {
        int[] copia = copiar(dados);
        long inicio = System.nanoTime();
        quickSort(copia, 0, copia.length - 1);
        long fim = System.nanoTime();
        return (fim - inicio) / 1_000_000.0;
    }

    // ALGORITMOS

    private static void bubbleSort(int[] v) {
        int n = v.length;
        boolean trocou;

        for (int i = 0; i < n - 1; i++) {
            trocou = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (v[j] > v[j + 1]) {
                    int tmp = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = tmp;
                    trocou = true;
                }
            }
            if (!trocou) break;
        }
    }

    private static void insertionSort(int[] v) {
        int n = v.length;
        for (int i = 1; i < n; i++) {
            int chave = v[i];
            int j = i - 1;

            while (j >= 0 && v[j] > chave) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = chave;
        }
    }

    private static void quickSort(int[] v, int inicio, int fim) {
        if (inicio < fim) {
            int p = particionar(v, inicio, fim);
            quickSort(v, inicio, p - 1);
            quickSort(v, p + 1, fim);
        }
    }

    private static int particionar(int[] v, int inicio, int fim) {
        int pivo = v[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (v[j] <= pivo) {
                i++;
                int tmp = v[i];
                v[i] = v[j];
                v[j] = tmp;
            }
        }

        int tmp = v[i + 1];
        v[i + 1] = v[fim];
        v[fim] = tmp;

        return i + 1;
    }

    private static int[] copiar(int[] v) {
        int[] c = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            c[i] = v[i];
        }
        return c;
    }
}