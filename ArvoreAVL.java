import java.util.ArrayList;
import java.util.Comparator;

public class ArvoreAVL{
    NoAVL raiz;
    int rotacoes;

    public ArvoreAVL(){
        this.raiz = null;
        this.rotacoes = 0;
    }

    public int getRotacoes() {
        return rotacoes;
    }

    public void inserir(Resultado resultado){
        raiz = inserirRec(raiz, resultado);
    }

    public int altura(NoAVL no){
        if(no == null)
        {
            return 0;
        }
        return no.altura;
    }

    public NoAVL inserirRec(NoAVL no, Resultado resultado){
        if(no == null)
        {
            return new NoAVL(resultado.similaridade, resultado);
        }

        if(resultado.similaridade == no.valor)
        {
            no.documentos_similaridade_iguais.add(resultado);
            return no;
        }

        if(resultado.similaridade < no.valor)
        {
            no.esq = inserirRec(no.esq, resultado);
        }
        else
        {
            no.dir = inserirRec(no.dir, resultado);
        }

        no.altura = Math.max(altura(no.esq), altura(no.dir)) + 1;

        int fb = Fator_Balenceamento(no);

        if (fb > 1 && resultado.similaridade < no.esq.valor)
        {
            return Rotacao_Direita(no);
        }

        if (fb < -1 && resultado.similaridade > no.dir.valor)
        {
            return Rotacao_Esquerda(no);
        }

        if (fb > 1 && resultado.similaridade > no.esq.valor)
        {
            no.esq = Rotacao_Esquerda(no.esq);
            return Rotacao_Direita(no);
        }

        if (fb < -1 && resultado.similaridade < no.dir.valor)
        {
            no.dir = Rotacao_Direita(no.dir);
            return Rotacao_Esquerda(no);
        }

        return no;

    }

    public int Fator_Balenceamento(NoAVL no){
        if(no == null)
        {
            return 0;
        }
        return altura(no.esq) - altura(no.dir);
    }

    public NoAVL Rotacao_Esquerda(NoAVL no){
        NoAVL x = no.dir;
        NoAVL y = x.esq;

        x.esq = no;
        no.dir = y;

        no.altura = Math.max(altura(no.esq), altura(no.dir)) + 1;
        x.altura = Math.max(altura(x.esq), altura(x.dir)) + 1;

        rotacoes++;
        return x;
    }

    private NoAVL Rotacao_Direita(NoAVL no) {
        NoAVL x = no.esq;
        NoAVL y = x.dir;

        x.dir = no;
        no.esq = y;

        no.altura = Math.max(altura(no.esq), altura(no.dir)) + 1;
        x.altura = Math.max(altura(x.esq), altura(x.dir)) + 1;

        rotacoes++;
        return x;
    }





    public void exibirEmOrdem() {
        exibirEmOrdem(raiz);
    }

    private void exibirEmOrdem(NoAVL no) {
        if (no != null) {
            exibirEmOrdem(no.esq);
            System.out.println("Similaridade: " + no.valor);
            for (Resultado r : no.documentos_similaridade_iguais)
            {
                System.out.println("   " + r);
            }
            exibirEmOrdem(no.dir);
        }
    }






    public void exibir_maior_limiar(double limiar){
        exibir_maior_limiar(limiar, raiz);
    }

    public void exibir_maior_limiar(double limiar, NoAVL no){
        if(no != null)
        {
            exibir_maior_limiar(limiar, no.esq);
            if(no.valor >= limiar)
            {
                for(Resultado r : no.documentos_similaridade_iguais)
                {
                    System.out.println("  " + r);
                }
            }
            exibir_maior_limiar(limiar, no.dir);
        }
    }

    public String exibir_maior_limiar_string(double limiar) {
        StringBuilder sb = new StringBuilder();
        exibir_maior_limiar_string(limiar, raiz, sb);

        return sb.toString();
    }


    public void exibir_maior_limiar_string(double limiar, NoAVL no, StringBuilder sb) {
        if (no == null)
        {
            return;
        }

        exibir_maior_limiar_string(limiar, no.esq, sb);

        if (no.valor >= limiar) {
            for (Resultado r : no.documentos_similaridade_iguais) {
                sb.append("  ")
                        .append(r.toString())
                        .append("\n");
            }
        }
        exibir_maior_limiar_string(limiar, no.dir, sb);
    }







    public void exibir_dois_arquivos(String doc1, String doc2){
        Resultado resultado = getResultado(raiz, doc1, doc2);

        if(resultado != null)
        {
            System.out.println("Comparando: " + doc1 + " <-> " + doc2);
            System.out.println("Similaridade Calculada: " + resultado.similaridade);
        } else
        {
            System.out.println("Nenhum resultado encontrado");
        }
    }

    public Resultado getResultado(NoAVL no, String doc1, String doc2){
        if(no == null)
        {
            return null;
        }

        Resultado esquerda = getResultado(no.esq, doc1, doc2);
        if (esquerda != null) return esquerda;


        for(Resultado r : no.documentos_similaridade_iguais)
        {
            if( (r.documento1.equals(doc1) && r.documento2.equals(doc2)) ||
                    (r.documento1.equals(doc2) && r.documento2.equals(doc1)))
            {
                return r;
            }
        }

        return getResultado(no.dir, doc1, doc2);
    }






    public void exibirTopKProximos(double limiar, int k) {
        ArrayList<Resultado> todos_resultados = new ArrayList<>();
        coletarResultados(raiz, todos_resultados);

        todos_resultados.sort(Comparator.comparingDouble(r -> Math.abs(r.similaridade - limiar)));

        System.out.println("Top " + k + " pares mais próximos de " + limiar + ":");
        int limite = Math.min(k, todos_resultados.size());
        for (int i = 0; i < limite; i++)
        {
            Resultado r = todos_resultados.get(i);
            System.out.println(r.documento1 + " " + r.documento2 + " = " + r.similaridade);
        }
    }

    public String exibirTopKproximos_string(double limiar, int k){
        StringBuilder sb = new StringBuilder();

        ArrayList<Resultado> todos_resultados = new ArrayList<>();
        coletarResultados(raiz, todos_resultados);

        todos_resultados.sort(Comparator.comparingDouble(r -> Math.abs(r.similaridade - limiar)));

        int limite = Math.min(k, todos_resultados.size());
        for (int i = 0; i < limite; i++)
        {
            Resultado r = todos_resultados.get(i);
            sb.append(r.documento1).append(" <-> ").append(r.documento2).append(" = ").append(r.similaridade).append("\n");
        }

        return sb.toString();
    }


    private void coletarResultados(NoAVL no, ArrayList<Resultado> lista){
        if (no == null)
        {
            return;
        }
        coletarResultados(no.esq, lista);

        lista.addAll(no.documentos_similaridade_iguais);

        coletarResultados(no.dir, lista);
    }






    public void menor_similaridade(){
        NoAVL no = menor_resultado(raiz);
        for(Resultado res : no.documentos_similaridade_iguais)
        {
            System.out.println(res.documento1 + " <-> " + res.documento2 + " = " + res.similaridade);
        }
    }

    public String menor_similaridade_string(){
        NoAVL no = menor_resultado(raiz);
        StringBuilder sb = new StringBuilder();
        for(Resultado res : no.documentos_similaridade_iguais)
        {
            sb.append(res.documento1).append(" <-> ").append(res.documento2).append(" = ").append(res.similaridade).append("\n");
        }
        return sb.toString();
    }

    public NoAVL menor_resultado(NoAVL no){
        if(no == null)
        {
            return null;
        }
        while(no.esq != null)
        {
            no = no.esq;
        }
        return no;
    }

}
