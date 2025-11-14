package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorCsvSimples {

    private static final int TAMANHO_MAXIMO = 10000;

    public static int[] lerArquivo(String caminho) throws IOException {
        int[] dadosTemporarios = new int[TAMANHO_MAXIMO];
        int contador = 0; 

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            br.readLine(); 

            String linha;
            while ((linha = br.readLine()) != null && contador < TAMANHO_MAXIMO) {
                
                if (linha.trim().isEmpty()) {
                    continue;
                }

                try {
                    int valor = Integer.parseInt(linha.trim()); 
                    dadosTemporarios[contador] = valor;
                    contador++;
                } catch (NumberFormatException e) {
                    System.err.println("Aviso: Pulando valor inválido: " + linha + " no arquivo: " + caminho);
                }
            }
        } 

        int[] dadosFinais = new int[contador];
        for (int i = 0; i < contador; i++) {
            dadosFinais[i] = dadosTemporarios[i];
        }

        return dadosFinais;
    }
}