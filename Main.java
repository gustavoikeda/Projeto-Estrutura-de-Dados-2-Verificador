import java.io.*;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws IOException {

        String pasta = args[0];
        double limiar = Double.parseDouble(args[1]);
        if(limiar < 0 || limiar > 1)
        {
            System.out.println("Limiar Inválido");
        }

        ArvoreAVL arvore = new ArvoreAVL();
        processarDocumentos(pasta, arvore);

        if(args.length == 3 && args[2].equals("lista"))
        {
            arvore.exibir_maior_limiar(limiar);
            System.out.println("pares com menor similaridade");
            arvore.menor_similaridade();
            int quant_arquivos = getQuantidadeArquivos(pasta);
            int quant_comaparacoes = getQuantidadeComparacoes(pasta, quant_arquivos);
            String saida = gerar_saida_padrao(arvore, quant_arquivos, quant_comaparacoes, limiar);
            criar_resultado(saida);
        }
        else if(args.length == 4 && args[2].equals("topK"))
        {
            int k = Integer.parseInt(args[3]);
            arvore.exibirTopKProximos(limiar, k);
            String saida_topK = gerar_saida_topK(arvore, k, limiar);
            criar_resultado(saida_topK);
        }
        else if(args.length == 5 && args[2].equals("busca"))
        {
            arvore.exibir_dois_arquivos(args[3], args[4]);
            String comparacao = gerar_saida_comparacao(arvore, args[3], args[4]);
            criar_resultado(comparacao);
        }


    }

    public static void processarDocumentos(String caminhoPasta, ArvoreAVL arvore) throws IOException {
        File pasta = new File(caminhoPasta);

        if (!pasta.exists() || !pasta.isDirectory()) {
            System.out.println("Diretório inválido: " + caminhoPasta);
            return;
        }

        File[] arquivos = pasta.listFiles();

        if (arquivos == null || arquivos.length == 0)
        {
            System.out.println("Nenhum arquivo .txt encontrado.");
            return;
        }

        ArrayList<Documento> docs = new ArrayList<>();

        for (File f : arquivos)
        {
            Documento d = new Documento(f.getAbsolutePath());
            d.processar(f.getAbsolutePath());
            docs.add(d);
        }


        for (int i = 0; i < docs.size(); i++) {
            for (int j = i + 1; j < docs.size(); j++) {

                Documento d1 = docs.get(i);
                Documento d2 = docs.get(j);

                Comparador comp = new Comparador(d1.documento, d2.documento);
                double sim = comp.Cosseno(d1,d2);

                Resultado r = new Resultado(d1.documento, d2.documento, sim);
                arvore.inserir(r);
            }
        }

        System.out.println("Comparações finalizadas.");
    }

    public static int getQuantidadeArquivos(String caminhoPasta){
        File pasta = new File(caminhoPasta);
        int cont = 0;
        File[] arquivos = pasta.listFiles();

        if (arquivos == null || arquivos.length == 0)
        {
            return 0;
        }

        for(int i = 0; i < arquivos.length; i++)
        {
            cont++;
        }
        return cont;
    }

    public static int getQuantidadeComparacoes(String caminhoPasta, int quantidade_arquivos){
        File pasta = new File(caminhoPasta);
        int cont = 0;
        File[] arquivos = pasta.listFiles();

        if (arquivos == null || arquivos.length == 0)
        {
            return 0;
        }

        for(int i = 0; i < quantidade_arquivos; i++){
            for(int j = i + 1; j < quantidade_arquivos; j++){
                cont++;
            }
        }
        return cont;
    }

    public static void criar_resultado(String saida) throws IOException {
        File novo_arquivo = new File("resultado.txt");
        if (novo_arquivo.createNewFile())
        {
            System.out.println("File created: " + novo_arquivo.getName());
        } else
        {
            System.out.println("File already exists.");
        }

        try(BufferedWriter escrever = new BufferedWriter(new FileWriter(novo_arquivo)))
        {
            escrever.write(saida);
        }
    }

    public static String gerar_saida_padrao(ArvoreAVL arvore, int quantidadeArquivos, int quantidadeComparacoes, double limiar){
        StringBuilder sb = new StringBuilder();

        sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");
        sb.append("Total de documentos processados: ").append(quantidadeArquivos).append("\n");
        sb.append("Total de pares comparados: ").append(quantidadeComparacoes).append("\n");
        sb.append("Função hash utilizada: Dispersão Dupla\n");
        sb.append("Métrica de similaridade: Cosseno\n");
        sb.append("Pares com similaridade >= ").append(limiar).append("\n");
        sb.append("---------------------------------").append("\n");
        String maiores = arvore.exibir_maior_limiar_string(limiar);
        String menores = arvore.menor_similaridade_string();
        sb.append(maiores);
        sb.append("Pares com menor similaridade: ").append("\n");
        sb.append("---------------------------------").append("\n");
        sb.append(menores);

        return sb.toString();
    }

    public static String gerar_saida_comparacao(ArvoreAVL arvore, String doc1, String doc2){
        StringBuilder sb = new StringBuilder();

        sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");
        sb.append("Comparando: ").append(doc1).append(" <-> ").append(doc2).append("\n");
        Resultado res = arvore.getResultado(arvore.raiz, doc1, doc2);
        sb.append("Similaridade calculada: ").append(res.similaridade).append("\n");
        sb.append("Métrica utilizada: Cosseno");

        return sb.toString();
    }

    public static String gerar_saida_topK(ArvoreAVL arvore, int k, double limiar){
        StringBuilder sb = new StringBuilder();
        sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");
        sb.append("Pares ").append(k).append(" mais semelhantes a: ").append(limiar).append("\n");
        String topK = arvore.exibirTopKproximos_string(limiar, k);
        sb.append(topK);

        return sb.toString();
    }

}
