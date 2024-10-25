package fc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {

    //create array list called cookies
    private List<String> cookies = new ArrayList<>();

    //create class called Cookie, with parameter String cookieFile
    public Cookie(String cookieFile) throws IOException {
        //to read text file
        FileReader fReader = new FileReader(cookieFile);
        BufferedReader bReader = new BufferedReader(fReader);
        //a place to temporarily store the line that is currently being read before added to arrayList cookies
        String line;

        while ((line = bReader.readLine()) != null) {
            cookies.add(line);
        }
        bReader.close();
    }

    public String getCookie() {
        if (cookies.isEmpty()) {
            return "No more cookies!";
        }
        //to randomize cookie drawn
        Random random = new Random();
        //to pick a random cookie from the bounds of the size of cookies arraylist
        //nextInt is to randomly generate a number
        return cookies.get(random.nextInt(cookies.size()));
    }
    
}
