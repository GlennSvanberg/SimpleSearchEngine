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

    private int calculateDocumentOccurences(List<String[]> documentWords, String term) {
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

    /*
     * IDF(t) = log_e(Total number of documents / Number of documents with term t in
     * it).
     */
    private void calculateIDF(List<String[]> documentWords) {
        for (String[] document : documentWords) {
            for (int i = 0; i < document.length; i++) {
                if (words.get(document[i]) == null) {
                    int occurences = calculateDocumentOccurences(documentWords, document[i]);

                    Double IDF = Math.log(document.length / Double.valueOf(occurences));
                    Word word = new Word(document[i]);
                    word.setIDF(IDF);
                    word.setTotalOccurences(occurences);
                    words.put(document[i], word);
                }
            }
        }
    }

    private int calculateOccurences(String[] document, String term) {
        int occurences = 0;
        for (int i = 0; i < document.length; i++) {
            if (document[i].equals(term)) {
                occurences++;
            }
        }
        return occurences;
    }

    /*
     * TF(t) = (Number of times term t appears in a document) / (Total number of
     * terms in the document)
     */
    private void calculateTF(List<String[]> documentTerms) {

        for (int i = 0; i < documentTerms.size(); i++) {
            String[] document = documentTerms.get(i);

            for (int j = 0; j < document.length; j++) {
                int occurences = calculateOccurences(document, document[j]);
                double tf = Double.valueOf(occurences) / document.length;
                double relevance = words.get(document[j]).getIDF() * tf;
                words.get(document[j]).addDocument(i, relevance);
            }
        }
    }

    private void printWords() {
        for (Map.Entry<String, Word> entry : words.entrySet()) {
            // System.out.println("IDF:" + entry.getValue().getIDF() + " Occurs in nr of
            // files: "
            // + entry.getValue().getTotalOccurences() + " Key:" + entry.getKey());

            for (int i = 0; i < entry.getValue().getDocumentReferences().size(); i++) {
                String t = "Index:" + entry.getValue().getDocumentReferences().get(i).getIndex() + " Relevance: "
                        + entry.getValue().getDocumentReferences().get(i).getRelevance();
                String word = entry.getValue().getName();
                System.out.println(t + " word: " + word);
            }
        }
    }

}