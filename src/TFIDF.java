import java.util.ArrayList;

public class TFIDF {
    public double tfIdf(HashTable doc, ArrayList<HashTable> docs, String term) {
        return Math.abs(tf(doc, term) * idf(docs, term));

    }
    public double tf(HashTable doc, String term) {
        double result = 0;

        if (doc.contains(term))
            result = doc.get(term);


        return result / doc.count;
    }
    public double idf(ArrayList<HashTable> docs, String term) {
        double count = 0;
        double result = 0;
        for (HashTable doc : docs) {
            for (int i = 0; i < doc.count; i++) {
                //HashTable temp = doc;
                if (doc.contains(term)) {
                    count++;
                }
            }
        }
        if(count > 0) {
            result =  Math.log(docs.size() / count);
        }

        return  result;
    }
}
