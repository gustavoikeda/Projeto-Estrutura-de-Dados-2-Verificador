import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws IOException {
        /*Hash h = new Hash(20);
        NoHash p1 = new NoHash(1, "gato");
        NoHash p2 = new NoHash(1, "gato");
        h.insersao_Dispersao_Dupla(p1);
        h.insersao_Dispersao_String(p2);
        h.exibir();
        String oi = "testando";
        String tchau = "falou";
        Resultado r = new Resultado(1);
        Resultado r2 = new Resultado(2);

        r.inserir_novo_documento(oi);
        r2.inserir_novo_documento(tchau);
        r.imprimir();
        r2.imprimir();
        ArvoreAVL arvore = new ArvoreAVL();

        arvore.inserir(new Resultado("doc1", "doc2", 70));
        arvore.inserir(new Resultado("doc34", "doc64", 55));
        arvore.inserir(new Resultado("doc76", "doc65", 40));
        arvore.inserir(new Resultado("doc35", "doc56", 85));
        arvore.inserir(new Resultado("doc87", "doc12", 90));
        arvore.inserir(new Resultado("doc32", "doc60", 72));
        arvore.inserir(new Resultado("doc53", "doc61", 42));
        arvore.inserir(new Resultado("doc67", "doc69", 70));// mesma similaridadeae

        //arvore.exibir_dois_arquivos("doc87", "doc12");

        //arvore.exibir_maior_limiar(42);
        arvore.exibirTopKProximos(42, 3);
        System.out.println("Rotações realizadas: " + arvore.getRotacoes());*/

        Documento dc = new Documento("C:\\Users\\User\\IdeaProjects\\Verificador\\src\\testando");
        dc.processar("C:\\Users\\User\\IdeaProjects\\Verificador\\src\\testando");
        Hash n = dc.tabela_hash;

        Documento dc2 = new Documento("C:\\Users\\User\\IdeaProjects\\Verificador\\src\\testing");
        dc2.processar("C:\\Users\\User\\IdeaProjects\\Verificador\\src\\testing");
        Hash n2 = dc2.tabela_hash;

        Documento dc3 = new Documento("C:\\Users\\User\\IdeaProjects\\Verificador\\src\\Gustavo");
        dc3.processar("C:\\Users\\User\\IdeaProjects\\Verificador\\src\\Gustavo");
        Hash n3 = dc3.tabela_hash;

        Documento dc4 = new Documento("C:\\Users\\User\\IdeaProjects\\Verificador\\src\\Jiye");
        dc4.processar("C:\\Users\\User\\IdeaProjects\\Verificador\\src\\Jiye");
        Hash n4 = dc4.tabela_hash;

        Comparador c = new Comparador(dc.documento, dc2.documento);
        double x = c.Cosseno(dc, dc2);
        System.out.println(x);

        Comparador c2 = new Comparador(dc3.documento, dc4.documento);
        double y = c.Cosseno(dc3, dc4);
        System.out.println(y);

        Resultado res = new Resultado(dc.documento, dc2.documento, x);

        Resultado res2 = new Resultado(dc3.documento, dc4.documento, y);

        ArvoreAVL ar = new ArvoreAVL();
        ar.inserir(res);
        ar.inserir(res2);
        ar.exibirEmOrdem();
    }
}
