import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main {



    public static void main(String[] args) throws IOException {


        Document doc = null;
        try {
            doc = Jsoup.connect(args[0]).get();

        } catch (IOException e) {
            e.printStackTrace();
        }


        String body = doc.body().text();
        TokenNameFinderModel model;
        try (InputStream modelIn = new FileInputStream("en-ner-person.bin")) {
            model = new TokenNameFinderModel(modelIn);

        }
        NameFinderME nameFinder = new NameFinderME(model);


        StringTokenizer st = new StringTokenizer(body);
        String[] liste = new String[st.countTokens()];





        for (int i = 0; st.hasMoreElements(); i++) {
            liste[i] = st.nextToken();


        }


        Span[] names = nameFinder.find(liste);
        displayNames(names, liste);

    }

    public static void displayNames(Span[] names,String[] tokens){
        for(int si=0; si<names.length;si++){
            StringBuilder cb=new StringBuilder();

            for(int ti=names[si].getStart();
                ti<names[si].getEnd();ti++){

                cb.append(tokens[ti]).append(" ");
            }
            System.out.println(cb.substring(0,cb.length()-1));
        }
    }


}