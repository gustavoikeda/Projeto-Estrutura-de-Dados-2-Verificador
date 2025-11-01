public class NoHash {
    int frequencia;
    String palavra;

    public NoHash(int frequencia, String palavra){
        this.frequencia = frequencia;
        this.palavra = palavra;
    }

    public int getLenght(){
        return palavra.length();
    }

    public String getPalavra(){
        return palavra;
    }

    public int getFrequencia(){
        return frequencia;
    }
}
