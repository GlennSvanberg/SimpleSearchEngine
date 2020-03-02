import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Word {
    private String name;
    private List<DocumentReference> documents;
    private double IDF;

    public Word(String name) {
        this.name = name;
        documents = new ArrayList<>();
    }

    public void setIDF(double IDF) {
        this.IDF = IDF;
    }
    public double getIDF() {
        return IDF;
    }

    public String getName() {
        return name;
    }

    public void addDocument(int index, double relevance) {
        documents.add(new DocumentReference(index, relevance));
        Collections.sort(documents);
    }

    public List<Integer> getDocumentIndex() {

        List<Integer> indexes = new ArrayList<>();
        for (DocumentReference dr : documents) {
            indexes.add(dr.getIndex());
        }
        return indexes;
    }
}