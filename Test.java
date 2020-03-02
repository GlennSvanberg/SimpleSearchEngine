import java.util.List;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        List<String> documents = new ArrayList<>();
        documents.add("the brown fox jumped over the brown dog");
        documents.add("the lazy brown dog sat in the corner");
        documents.add("the red fox bit the lazy dog");

        SimpleSearchEngine SSE = new SimpleSearchEngine(documents);

        // SSE.query("fox");
    }
}