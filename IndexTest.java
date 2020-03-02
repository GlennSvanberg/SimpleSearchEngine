import java.util.List;

public class IndexTest {
    public static void main(String[] args) {
        Word fox = new Word("fox");
        fox.addDocument(0, 1);
        fox.addDocument(1, 2);
        fox.addDocument(3, 5);

        List<Integer> indexes = fox.getDocumentIndex();
        for (int i = 0; i < indexes.size(); i++) {
            System.out.println("Document: " + indexes.get(i));
        }

        
    }
}