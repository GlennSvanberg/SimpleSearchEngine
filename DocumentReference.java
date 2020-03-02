public class DocumentReference implements Comparable<DocumentReference> {
    private int index;
    private double relevance;

    public DocumentReference(int index, double relevance) {
        this.index = index;
        this.relevance = relevance;
    }

    public int getIndex() {
        return index;
    }

    public double getRelevance() {
        return relevance;
    }

    @Override
    public int compareTo(DocumentReference other) {

        double res = other.getRelevance() - relevance;

        if (res > 0) {
            return 1;
        } else if (res < 0) {
            return -1;
        }
        return 0;
    }

}