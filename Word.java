import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Word {
    private String name;
    private List<DocumentReference> documents;
    private double IDF;

    public Word(String name, double IDF) {
        this.name = name;
        this.IDF = IDF;
        documents = new ArrayList<>();
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

    public List<DocumentReference> getDocumentReferences() {
        return documents;
    }
}