import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleSearchEngine {
    private Map<String, Word> wordIndex;
    private List<String> documents;

    public SimpleSearchEngine(List<String> documents) {
        if (documents.size() == 0) {
            throw new IllegalArgumentException();
        }

        this.documents = documents;
        wordIndex = new HashMap<>();

        List<String[]> documentWordList = parseDocuments();
        calculateIDF(documentWordList);
        calculateTF(documentWordList);
    }

    public List<String> query(String term) {
        List<String> result = new ArrayList<>();
        if (wordIndex.get(term) == null) {
            return result;
        }

        List<DocumentReference> references = wordIndex.get(term).getDocumentReferences();

        for (int i = 0; i < references.size(); i++) {
            result.add(documents.get(references.get(i).getIndex()));
        }
        return result;
    }

    private List<String[]> parseDocuments() {
        List<String[]> documentWordList = new ArrayList<>();

        for (String document : documents) {
            String[] words = document.split(" ");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].toLowerCase();
            }
            documentWordList.add(words);
        }
        return documentWordList;
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

    private int calculateOccurences(String[] document, String term) {
        int occurences = 0;
        for (int i = 0; i < document.length; i++) {
            if (document[i].equals(term)) {
                occurences++;
            }
        }
        return occurences;
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
}