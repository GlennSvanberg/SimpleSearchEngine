import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleSearchEngine {
    private Map<String, Word> words;
    private List<String> documents;

    public SimpleSearchEngine(List<String> documents) {
        this.documents = documents;
        words = new HashMap<>();
        calculateRelevance();
    }

    private void calculateRelevance() {
        List<String> allTerms = new ArrayList<>();
        List<String[]> documentTerms = new ArrayList<>();

        for (String document : documents) {
            String[] terms = document.split(" ");
            documentTerms.add(terms);

            for (int i = 0; i < terms.length; i++) {
                allTerms.add(terms[i]);
            }
        }
        calculateIDF(allTerms);
        calculateTF(documentTerms);
        printWords();

    }

    private void calculateIDF(List<String> terms) {
        for (String term : terms) {
            if (words.get(term) == null) {
                int occurences = 0;
                for (String t : terms) {
                    if (t.equals(term)) {
                        occurences++;
                    }
                }
                Double IDF = Double.valueOf(occurences) / terms.size();
                Word word = new Word(term);
                word.setIDF(IDF);
                words.put(term, word);
            }
        }
    }

    private void calculateTF(List<String[]> documentTerms) {
        for (String[] words : documentTerms) {

        }
    }

    private void printWords() {
        for (Map.Entry<String, Word> entry : words.entrySet()) {
            System.out.println("Key:" + entry.getKey() + " Word:" + entry.getValue().getName() + " IDF:"
                    + entry.getValue().getIDF());
        }
    }

}