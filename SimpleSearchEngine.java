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
        List<String[]> documentTerms = new ArrayList<>();

        for (String document : documents) {
            String[] terms = document.split(" ");
            documentTerms.add(terms);
        }
        calculateIDF(documentTerms);
        calculateTF(documentTerms);
        printWords();

    }

    private int calculateOccurences(List<String[]> documentWords, String term) {
        int occurences = 0;
        for (String[] document : documentWords) {
            for (int i = 0; i < document.length; i++) {
                if (document[i].equals(term)) {
                    occurences++;
                    break;
                }
            }
        }
        return occurences;
    }

    private void calculateIDF(List<String[]> documentWords) {
        for (String[] document : documentWords) {
            for (int i = 0; i < document.length; i++) {
                if (words.get(document[i]) == null) {
                    int occurences = calculateOccurences(documentWords, document[i]);

                    Double IDF = Math.log(document.length / Double.valueOf(occurences));
                    Word word = new Word(document[i]);
                    word.setIDF(IDF);
                    word.setTotalOccurences(occurences);
                    words.put(document[i], word);
                }
            }
        }
    }

    private void calculateTF(List<String[]> documentTerms) {
        for (String[] words : documentTerms) {

        }
    }

    private void printWords() {
        for (Map.Entry<String, Word> entry : words.entrySet()) {
            System.out.println("IDF:" + entry.getValue().getIDF() + " Occurs in nr of files: "
                    + entry.getValue().getTotalOccurences() + " Key:" + entry.getKey());
        }
    }

}