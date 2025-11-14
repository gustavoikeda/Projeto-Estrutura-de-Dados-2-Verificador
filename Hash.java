public class Hash {
    NoHash[] tabela;
    int tamanho;

    public Hash(int tamanho){
        this.tabela = new NoHash[tamanho];
        this.tamanho = tamanho;
    }

    public int h1(int x){
        return x % tamanho;
    }

    public int h2(int x){
        return 1 + (x % (tamanho - 1));
    }

    public int h(int x, int k){
        return (h1(x) + k * h2(x)) % tamanho;
    }

    public int dispersao_String(NoHash no){
        int h = 0;
        for(int i = 0; i < no.getLenght(); i++)
        {
            h = (31 * h + no.getPalavra().charAt(i)) % tamanho;
        }
        return h;
    }

    public int hash_String(String palavra){
        int h = 0;
        for(int i = 0; i < palavra.length(); i++)
        {
            h = (31 * h + palavra.charAt(i)) % tamanho;
        }
        return h;
    }


    public void insersao_Dispersao_Dupla(NoHash no){
        int k = 0;
        int valor = hash_String(no.getPalavra());
        while(k != tamanho)
        {
            int posicao = h(valor, k);
            if(tabela[posicao] == null)
            {
                tabela[posicao] = no;
                return;
            }
            else if(tabela[posicao].getPalavra().equals(no.getPalavra()))
            {
                tabela[posicao].frequencia += no.getFrequencia();
                return;
            }
            k++;
        }
    }

    public void insersao_Dispersao_String(NoHash no){
        int k = 0;
        int valor = dispersao_String(no);
        while(k != tamanho)
        {
            int posicao = h(valor, k);
            if(tabela[posicao] == null)
            {
                tabela[posicao] = no;
                return;
            }
            else if(tabela[posicao].getPalavra().equals(no.getPalavra()))
            {
                tabela[posicao].frequencia += no.getFrequencia();
                return;
            }
            k++;
        }
    }

    public NoHash buscar(String palavra){
        int k = 0;
        int valor = hash_String(palavra);
        while(k < tamanho)
        {
            int posicao = h(valor, k);
            NoHash no = tabela[posicao];
            if(no == null)
            {
                return null;
            }
            if(no.getPalavra().equals(palavra))
            {
                return no;
            }
            k++;
        }
        return null;
    }

    public void exibir() {
        System.out.println("\nTabela Hash:");
        for (int i = 0; i < tamanho; i++)
        {
            System.out.println(i + " → " + (tabela[i] != null ? tabela[i].getPalavra() + tabela[i].getFrequencia() : "vazio"));
        }
    }
}


