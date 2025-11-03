import java.util.ArrayList
public class NoArvore{
    int similaridade;
    ArrayList<String> documentos;
    NoArvore prox;
    
    public NoArvore(int similaridade){
        this.similaridade = similaridade;
        this.documentos = new ArrayList<>();
        this.prox = null;
    }
    
    public void inserir_novo_documentos(String novo_documento){
        documentos.add(novo_documento);
    }
}
