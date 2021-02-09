import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import java.io.*;
import java.util.ArrayList;

public class FrequencyTables {
    public final int SIZE = 20;
    public Document input;

    public String initialize(Document userUrl) throws IOException {
        input = userUrl;
        ArrayList<Document> urlList = new Cache().Crawl();
        ArrayList<HashTable> urls = new ArrayList<>();
        HashTable urlIn = new HashTable();
        String[] allUrls = new String[SIZE];
        String[] urlNames = new String [SIZE];
        String workingDirectory = System.getProperty("user.dir");
        double[] tfidfVal = new double[SIZE];
        Document doc;
        String[] inputWords;
        String finalLink;

        for(int i =0; i < urlList.size(); i++){

            urlNames[i] = urlList.get(i).baseUri();
            allUrls[i] = workingDirectory + "/" + urlList.get(i).title().replaceAll("[^a-zA-Z0-9]", "") + ".txt";
        }

        for(int i= 0; i < allUrls.length; i++){
               try {
                    HashTable urlHash = new HashTable();
                    if (allUrls[i] != null) {
                        doc = Jsoup.parse(new File(allUrls[i]), "ISO-8859-1");
                        doc = new Cleaner(Whitelist.basic()).clean(doc);
                        String [] urlWords = doc.body().text().split(" ");

                        for(String word : urlWords){
                            urlHash.add(word.toLowerCase());
                        }
                        urls.add(urlHash);

                    }
               } catch(FileNotFoundException e){
                    e.printStackTrace();
               } catch(IOException e){
                   e.printStackTrace();
               }
        }

        inputWords = input.body().text().split(" ");

        for(int i =0; i < inputWords.length; i++){
            urlIn.add(inputWords[i].toLowerCase());
        }

        for(int i=0; i < urls.size(); i++){
            HashTable.Node temp= urlIn.first;
            while(temp != null){
                tfidfVal[i] += new TFIDF().tfIdf(urls.get(i),urls,temp.key);
                System.out.println(i + ": The value is: " + tfidfVal[i]);
                temp = temp.next;
            }
        }

        finalLink = finalUrl(urlNames,tfidfVal);
        System.out.println("This is the final link " + finalLink);

        return finalLink;
    }

    public String finalUrl(String[] links, double [] vals){
        double max = vals[0];
        int pos = 0;
        String result;

        for(int i=0; i < vals.length; i++){
            if(vals[i] > max)
                max = vals[i];
        }

        while(vals[pos] != max) {
            pos++;
        }
        //System.out.println("This is the max position: " + pos);
        result = links[pos];
        return result;
    }

}
