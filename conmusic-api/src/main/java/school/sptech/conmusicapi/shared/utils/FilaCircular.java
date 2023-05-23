package org.example;

public class FilaCircular {
    int tamanho, inicio, fim;
    String[] fila;

    // Construtor - Recebe a capacidade da fila (tamanho total do vetor)
    public FilaCircular(int capacidade) {
        fila = new String[capacidade];
        tamanho = 0;
        inicio = 0;
        fim = 0;
    }

    // Método isEmpty() - Retorna true se a fila está vazia e false caso contrário
    public boolean isEmpty() {
        return tamanho == 0;
    }

    // Método isFull() - Retorna true se a fila está cheia e false caso contrário
    public boolean isFull() {
        return tamanho == fila.length;
    }

    // Método insert - Recebe informação a ser inserida na fila
    public void insert(String info) {
        if (isFull()) {
            throw new IllegalStateException("Fila está cheia");
        } else {
            fila[fim] = info;
            fim = (fim + 1) % fila.length;
            tamanho++;
        }
    }

    // Método peek() - Retorna o primeiro da fila, sem remover
    public String peek() {
        if (isEmpty()) {
            return null;
        } else {
            return fila[inicio];
        }
    }

    // Método poll() - Retorna o primeiro da fila, removendo-o
    public String poll() {
        if (isEmpty()) {
            return null;
        } else {
            String valor = fila[inicio];
            fila[inicio] = null;
            inicio = (inicio + 1) % fila.length;
            tamanho--;
            return valor;
        }
    }

    // Método exibe() - exibe os elementos da fila
    public void exibe() {
        System.out.print("FilaCircular [");
        for (int i = inicio, index = 0; index < tamanho; i = (i + 1) % fila.length, index++) {
            System.out.print('"' + fila[i] + '"' + ((index + 1) < tamanho ? ", " : "]"));
        }
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFim() {
        return fim;
    }

    // Cria um vetor e percorre a fila adicionando os elementos no vetor (
    // Retorna o vetor criado e não a fila
    // Esse método é equivalente ao exibe, mas em vez de exibir os elementos da fila na console,
    // copia os elementos da fila para um vetor, na ordem em que seriam exibidos
    public String[] getFila(){
        String[] valores = new String[tamanho];

        int index = 0;
        int i = inicio;
        while (index < valores.length) {
            valores[index++] = fila[i];
            i = (i + 1) % fila.length;
        }

        return valores;
    }
}

