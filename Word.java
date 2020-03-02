import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Word {
    private List<DocumentReference> documents;
    private double idf;

    public Word(double idf) {
        this.idf = idf;
        documents = new ArrayList<>();
    }

    public double getIdf() {
        return idf;
    }

    public void addDocument(int index, double relevance) {
        documents.add(new DocumentReference(index, relevance));
        Collections.sort(documents);
    }

    public List<DocumentReference> getDocumentReferences() {
        return documents;
    }
}