import java.util.List;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        List<String> documents = new ArrayList<>();
        documents.add("the brown fox jumped over the brown dog");
        documents.add("the lazy brown dog sat in the corner");
        documents.add("the red fox bit the lazy dog");

        SimpleSearchEngine SSE = new SimpleSearchEngine(documents);

        List<String> result = SSE.query("red");

        // prints the document for every time the word occurs in the document
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
}