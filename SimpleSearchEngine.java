import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * IDF(t) = log_e(Total number of documents / Number of documents with term t in
 * it).
 * TF(t) = (Number of times term t appears in a document) / (Total number of
 * terms in the document)
 */

public class SimpleSearchEngine {
    private Map<String, Word> wordIndex;
    private List<String> documents;

    /*
     * Takes a list of strings and stores the words in memory as a inverted index.
     * The index maps all the words to each document they occur in with the
     * relevance to each document.
     */
    public SimpleSearchEngine(List<String> documents) {

        if (documents.size() == 0) {
            throw new IllegalArgumentException();
        }

        this.documents = documents;
        wordIndex = new HashMap<>();
        List<String[]> documentWords = parseDocuments();
        calculateRelevances(documentWords);
    }

    /*
     * Given a search term this method will return a list of all the documents
     * containing the specific term in order based on the terms relevance to the
     * document.
     * 
     * The relevance is calculated using TF-IDF where TF is term frequency adjusted
     * for document length and IDF represents how common the word is in other
     * documents
     */
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
        List<String[]> documentWords = new ArrayList<>();

        for (String document : documents) {
            String[] words = document.split(" ");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].toLowerCase();
            }
            documentWords.add(words);
        }
        return documentWords;
    }

    private void calculateRelevances(List<String[]> documentWords) {

        calculateIdf(documentWords);

        for (int i = 0; i < documentWords.size(); i++) {
            String[] document = documentWords.get(i);

            for (int j = 0; j < document.length; j++) {
                if (!documentIsAddedToIndex(i, document[j])) {

                    int occurences = occurencesInDocument(document, document[j]);
                    double tf = Double.valueOf(occurences) / document.length;
                    double relevance = wordIndex.get(document[j]).getIdf() * tf;

                    wordIndex.get(document[j]).addDocument(i, relevance);
                }
            }
        }
    }

    private void calculateIdf(List<String[]> documentWords) {
        for (String[] document : documentWords) {
            for (int i = 0; i < document.length; i++) {
                if (wordIndex.get(document[i]) == null) {
                    int occurences = occurencesInAllDocuments(documentWords, document[i]);

                    double idf = Math.log(document.length / Double.valueOf(occurences));
                    Word word = new Word(idf);
                    wordIndex.put(document[i], word);
                }
            }
        }
    }

    private int occurencesInAllDocuments(List<String[]> documentWords, String term) {
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

    private int occurencesInDocument(String[] document, String term) {
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
        for (DocumentReference documentReference : references) {
            if (documentReference.getIndex() == index) {
                return true;
            }
        }
        return false;
    }
}