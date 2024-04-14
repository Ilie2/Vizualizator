package Binomial;

import java.util.ArrayList;
import java.util.List;

class NodBinomial<T extends Comparable<T>> {
    T cheie;
    int grad;
    List<NodBinomial<T>> copii;

    public NodBinomial(T cheie) {
        this.cheie = cheie;
        this.grad = 0;
        this.copii = new ArrayList<>();
    }
}
