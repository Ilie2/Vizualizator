package Binomial;

import java.util.ArrayList;
import java.util.List;

public class Heap<T extends Comparable<T>> {
    private List<NodBinomial<T>> radacini;

    public Heap() {
        this.radacini = new ArrayList<>();
    }

    public void insereaza(T cheie) {
        Heap<T> tempHeap = new Heap<>();
        tempHeap.radacini.add(new NodBinomial<>(cheie));
        unesteHeaps(tempHeap);
    }

    public void unesteHeaps(Heap<T> heapToMerge) {
        this.radacini.addAll(heapToMerge.radacini);
        consolideaza();
    }

    private void consolideaza() {
        List<NodBinomial<T>> radaciniConsolidate = new ArrayList<>();
        int maxDegree = getMaxDegree();

        for (int i = 0; i <= maxDegree; i++) {
            radaciniConsolidate.add(null);
        }

        for (NodBinomial<T> nod : this.radacini) {
            if (nod != null) { // Adaugă această verificare
                int grad = nod.grad;

                while (radaciniConsolidate.get(grad) != null) {
                    NodBinomial<T> existent = radaciniConsolidate.get(grad);
                    nod = link(nod, existent);
                    radaciniConsolidate.set(grad, null);
                    grad++;
                }

                radaciniConsolidate.set(grad, nod);
            }
        }

        this.radacini = radaciniConsolidate;
    }

    private int getMaxDegree() {
        int maxDegree = 0;

        for (NodBinomial<T> nod : this.radacini) {
            if (nod != null) {
                maxDegree = Math.max(maxDegree, nod.grad);
            }
        }

        return maxDegree;
    }


    private NodBinomial<T> link(NodBinomial<T> maiMic, NodBinomial<T> maiMare) {
        maiMic.copii.add(maiMare);
        maiMic.grad++;
        return maiMic;
    }

    public T extrageMinim() {
        if (this.radacini.isEmpty()) {
            return null;
        }

        NodBinomial<T> minim = this.radacini.get(0);
        for (NodBinomial<T> nod : this.radacini) {
            if (nod.cheie.compareTo(minim.cheie) < 0) {
                minim = nod;
            }
        }

        this.radacini.remove(minim);

        Heap<T> tempHeap = new Heap<>();
        tempHeap.radacini.addAll(minim.copii);
        unesteHeaps(tempHeap);

        return minim.cheie;
    }

    public boolean esteGol() {
        return this.radacini.isEmpty();
    }

    public T extractMin() {
        if (esteGol()) {
            return null;
        }

        NodBinomial<T> minNode = findMinNode();
        radacini.remove(minNode);

        Heap<T> tempHeap = new Heap<>();
        tempHeap.radacini.addAll(minNode.copii);
        unesteHeaps(tempHeap);

        return minNode.cheie;
    }

    private NodBinomial<T> findMinNode() {
        NodBinomial<T> minNode = radacini.get(0);
        for (NodBinomial<T> node : radacini) {
            if (node.cheie.compareTo(minNode.cheie) < 0) {
                minNode = node;
            }
        }
        return minNode;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (NodBinomial<T> nod : this.radacini) {
            result.append(toStringHelper(nod)).append("\n");
        }
        return result.toString();
    }

    private String toStringHelper(NodBinomial<T> nod) {
        if (nod == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(nod.cheie).append(" ");

        for (NodBinomial<T> copil : nod.copii) {
            result.append(toStringHelper(copil));
        }

        return result.toString();
    }


}
