public class Hash {
    NoHash[] tabela;
    int tamanho;

    public Hash(int tamanho){
        this.tabela = new NoHash[tamanho];
        this.tamanho = tamanho;
    }

    public int h1(int x){
        return x % 17;
    }

    public int h2(int x){
        return 1 + (x % 13);
    }

    public int h(int x, int k){
        return h1(x) + (k * h2(x));
    }

    public void inserir(NoHash no){
        int k = 0;
        int posicao;
        while(k != tamanho)
        {
            posicao = h(no.getLenght(), k) % tamanho;
            if(tabela[posicao] == null)
            {
                tabela[posicao] = no;
                return;
            }
            k++;
        }
    }

    public void exibir() {
        System.out.println("\nTabela Hash:");
        for (int i = 0; i < tamanho; i++) {
            System.out.println(i + " → " + (tabela[i] != null ? tabela[i].getPalavra() + tabela[i].getFrequencia() : "vazio"));
        }
    }
}


