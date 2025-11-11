public class Resultado {
    float similaridade;
    String documento1;
    String documento2;

    public Resultado(float similaridade, String documento1, String documento2){
        this.similaridade = similaridade;
        this.documento1 = documento1;
        this.documento2 = documento2;
    }

    @Override
    public String toString() {
        return "(" + documento1 + ", " + documento2 + ") = " + String.format("%.4f", similaridade);
    }
}
