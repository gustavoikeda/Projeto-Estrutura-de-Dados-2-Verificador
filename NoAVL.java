import java.util.ArrayList;

public class NoAVL {
    float valor;
    ArrayList<Resultado> documentos_similaridade_iguais;
    NoAVL dir;
    NoAVL esq;
    int altura;

    public NoAVL(float similaridade, Resultado resultado){
        this.valor = similaridade;
        this.documentos_similaridade_iguais = new ArrayList<>();
        this.documentos_similaridade_iguais.add(resultado);
        this.dir = null;
        this.esq = null;
        this.altura = 1;
    }
