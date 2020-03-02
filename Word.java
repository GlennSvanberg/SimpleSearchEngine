import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {
    private String name;
    private List<DocumentReference> documents;

    public Word(String name) {
        this.name = name;
        documents = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addDocument(int index, double relevance) {
        documents.add(new DocumentReference(index, relevance));
        Collections.sort(documents);
    }

    public List<Integer> getDocumentIndex() {
        // Sort the map and return list of indexes in order
        List<Integer> indexes = new ArrayList<>();

    }
}