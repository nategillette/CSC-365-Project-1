import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Cache {

    final static int MAX = 10;
    private static ArrayList<Document> urlList = new ArrayList<>();

    public ArrayList<Document> Crawl() throws IOException {
        Document doc;
        BufferedWriter bw;
        String fileName;
        File urlFile = new File("/media/nate/FILES/CSC_365_Project_1/URLS.txt");
        Scanner sc = new Scanner(urlFile);
        String[] urls = sc.next().split(",");

        for(String url: urls){
            doc = Jsoup.connect(url).get();
            fileName = doc.title().replaceAll("[^a-zA-Z0-9]", "");
            //File file = new File(workingDirectory, fileName + ".txt");
            FileWriter fw = new FileWriter(fileName + ".txt");
            bw = new BufferedWriter(fw);
            bw.write(doc.toString());
            urlList.add(doc);
        }
            doc = Jsoup.connect("https://en.wikipedia.org/wiki/Computer").get();

            for (int i = 0; i < MAX; i++) {

                fileName = doc.title().replaceAll("[^a-zA-Z0-9]", "");
                FileWriter fw = new FileWriter(fileName + ".txt");
                bw = new BufferedWriter(fw);
                bw.write(doc.toString());
                urlList.add(doc);

                doc = nextUrl(doc);

            }
        sc.close();
       return urlList;
    }
  private Document nextUrl(Document doc) throws IOException {
        Document newUrl;
        Random r = new Random();
        ArrayList<String> linkList = new ArrayList<>();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String url = link.attr("abs:href");

            if (!url.startsWith("https://en.wikipedia.org") || !url.contains("/wiki/") || url.substring(6).contains(":")
                    || url.contains("#") || url.contains("%"))
                continue;

            linkList.add(url);
        }
        newUrl = Jsoup.connect(linkList.get(r.nextInt(linkList.size()))).get();
        return newUrl;
    }
}
