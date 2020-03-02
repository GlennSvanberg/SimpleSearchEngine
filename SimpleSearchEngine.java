import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleSearchEngine {
    private Map<String, Word> wordIndex;
    private List<String> documents;

    public SimpleSearchEngine(List<String> documents) {
        this.documents = documents;
        wordIndex = new HashMap<>();
        calculateRelevance();
    }

    public List<String> query(String term) {
        List<DocumentReference> references = wordIndex.get(term).getDocumentReferences();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < references.size(); i++) {
            result.add(documents.get(references.get(i).getIndex()));
        }
        return result;
    }

    private void calculateRelevance() {
        List<String[]> documentTerms = new ArrayList<>();

        for (String document : documents) {
            String[] terms = document.split(" ");
            documentTerms.add(terms);
        }
        calculateIDF(documentTerms);
        calculateTF(documentTerms);
    }

    private int calculateOccurencesInDocuments(List<String[]> documentWords, String term) {
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
                if (wordIndex.get(document[i]) == null) {
                    int occurences = calculateOccurencesInDocuments(documentWords, document[i]);

                    Double IDF = Math.log(document.length / Double.valueOf(occurences));
                    Word word = new Word(document[i], IDF);
                    wordIndex.put(document[i], word);
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
                if (!documentIsAddedToIndex(i, document[j])) {
                    int occurences = calculateOccurences(document, document[j]);
                    double tf = Double.valueOf(occurences) / document.length;
                    double relevance = wordIndex.get(document[j]).getIDF() * tf;
                    wordIndex.get(document[j]).addDocument(i, relevance);
                }

            }
        }
    }

    private boolean documentIsAddedToIndex(int index, String term) {

        List<DocumentReference> references = wordIndex.get(term).getDocumentReferences();
        for (DocumentReference dr : references) {
            if (dr.getIndex() == index) {
                return true;
            }
        }
        return false;
    }

    private void printWords() {
        for (Map.Entry<String, Word> entry : wordIndex.entrySet()) {
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