public class ArvoreAVL{
    NoAVL raiz;
    int rotacoes;

    public ArvoreAVL(){
        this.raiz = null;
        this.rotacoes = 0;
    }

    public void inserir(Resultado resultado){
        raiz = inserirRec(raiz, resultado);
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

    public int altura(NoAVL no){
        if(no == null)
        {
            return 0;
        }
        return no.altura;
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
            for (Resultado r : no.documentos_similaridade_iguais) {
                System.out.println("   " + r);
            }
            exibirEmOrdem(no.dir);
        }
    }

    public int getRotacoes() {
        return rotacoes;
    }
}
