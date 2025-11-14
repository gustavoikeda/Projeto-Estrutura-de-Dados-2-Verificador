public class Comparador {
    String nome_documento1;
    String nome_docuemnto2;

    public Comparador(String nome_documento1, String nome_docuemnto2){
        this.nome_documento1 = nome_documento1;
        this.nome_docuemnto2 = nome_docuemnto2;
    }

    public double Cosseno(Documento documento1, Documento documento2){
        Hash tabela1 = documento1.tabela_hash;
        Hash tabela2 = documento2.tabela_hash;

        double produto_escalar = 0;
        double moduloA = 0;
        double moduloB = 0;
        for (int i = 0; i < tabela1.tamanho; i++) {

            NoHash no1 = tabela1.tabela[i];
            if (no1 != null) {

                String palavra = no1.getPalavra();
                int freq1 = no1.getFrequencia();

                NoHash no2 = tabela2.buscar(palavra);
                int freq2 = (no2 != null) ? no2.getFrequencia() : 0;

                produto_escalar += (freq1 * freq2);
                moduloA += Math.pow(freq1, 2);
            }
        }
        for (int i = 0; i < tabela2.tamanho; i++) {

            NoHash no2 = tabela2.tabela[i];
            if (no2 != null) {

                int freq2 = no2.getFrequencia();
                moduloB += Math.pow(freq2, 2);
            }
        }
        moduloA = Math.sqrt(moduloA);
        moduloB = Math.sqrt(moduloB);

        if (moduloA == 0 || moduloB == 0)
        {
            return 0;
        }
        double resultado = produto_escalar / (moduloA * moduloB);

        return Math.round(resultado * 100.0) / 100.0;
    }
}
