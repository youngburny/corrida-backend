package br.com.corrida.dto;

public class ResumoResponse {

    private int total;
    private int km5;
    private int km10;

    public ResumoResponse(int total, int km5, int km10) {
        this.total = total;
        this.km5 = km5;
        this.km10 = km10;
    }

    public int getTotal() {
        return total;
    }

    public int getKm5() {
        return km5;
    }

    public int getKm10() {
        return km10;
    }
}
