import java.io.*;
import java.util.*;

public class Documento {
    String documento;
    Hash tabela_hash;

    public Documento(String documento){
        this.documento = new File(documento).getName();
        this.tabela_hash = new Hash(50);
    }

    public String lerArquivo(String caminho) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;
        while ((linha = br.readLine()) != null)
        {
            sb.append(linha).append(" ");
        }
        br.close();
        return sb.toString();
    }

    public List<String> normalizar_tokenizar(String texto){
        texto = texto.toLowerCase();
        texto = texto.replaceAll("[^a-zà-ù]", " ");
        String[] tokens = texto.split("\\s+");
        return Arrays.asList(tokens);
    }

    public List<String> remover_stopwords(List<String> token){
        Set<String> sw = stop_words();
        List<String> vocabulario = new ArrayList<>();
        for(String palavra : token){
            if(!sw.contains(palavra))
            {
                vocabulario.add(palavra);
            }
        }
        return vocabulario;
    }

    public Set<String> stop_words(){
        return new HashSet<>(Arrays.asList(
                "de", "em", "por", "para", "com", "sem", "sob", "sobre", "entre", "até",
                "desde", "contra", "perante", "através", "além", "dentro", "fora", "perto",
                "longe", "durante", "a", "as", "o", "os", "de", "da", "do", "dos", "das",
                "em", "no", "na", "nos", "nas", "e", "é", "por", "para",
                "com", "um", "uma", "uns", "umas", "ao", "aos", "à", "às",
                "se", "que", "como", "também", "mas", "ou", "são"));
    }

    public void processar(String caminho) throws IOException {
        String s = lerArquivo(caminho);
        List<String> normalizado = normalizar_tokenizar(s);
        List<String> vocabulario = remover_stopwords(normalizado);
        inserir_vocabulario(vocabulario);
    }

    public void inserir_vocabulario(List<String> vocabulario){
        for(String palavra : vocabulario)
        {
            NoHash novo_no = new NoHash(1, palavra);
            this.tabela_hash.insersao_Dispersao_String(novo_no);
        }
    }

}
