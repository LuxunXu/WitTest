import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WitTest {

    // private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        //checkStatus("QHP5YXMFATLYE2EGODMPMWU6ZWO5VI6L");
        checkMessage("TOQBHMSB4YPTIU7LZUBE2KCSH5ZRISEP", "Are there income guidelines?");
        checkMessage("GV2XNT54SLTGSZQHG5WZOEH4ODEGIPG4", "Are there income guidelines to receive the meals?");
        //checkStatus("TOQBHMSB4YPTIU7LZUBE2KCSH5ZRISEP");
        //newApp("testApp");
        //addEntity("mealQuestion", "TOQBHMSB4YPTIU7LZUBE2KCSH5ZRISEP");
        //trainTopicEntity("topic", "Commodity_Boxes", "Commodity_Boxes", "TOQBHMSB4YPTIU7LZUBE2KCSH5ZRISEP");
        //addWitValue("mealQuestion", "incomeGuidelineMeal", "TOQBHMSB4YPTIU7LZUBE2KCSH5ZRISEP");
        //addWitExpression("farmers_market_vouchersQuestion", "Are there income guidelines","incomeGuideline", "TOQBHMSB4YPTIU7LZUBE2KCSH5ZRISEP");

    }

    private static void checkStatus(String token) {
        try {
            URL myURL = new URL("https://api.wit.ai/samples?limit=1000");
            URLConnection conn = myURL.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + token); // QHP5YXMFATLYE2EGODMPMWU6ZWO5VI6L for CICOA
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String inputLine;
            String output = "";
            while ((inputLine = in.readLine()) != null) {
                output += inputLine + "\n";
            }
            in.close();
            System.out.println(output);
        } catch (MalformedURLException e) {
            // new URL() failed
            // ...
        } catch (IOException e) {
            // openConnection() failed
            // ...
        }
    }

    private static void checkMessage(String token, String message) {
        message = message.replaceAll(" ", "%20");
        try {
            URL myURL = new URL("https://api.wit.ai/message?v=20170307&q=" + message);
            URLConnection conn = myURL.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + token); // QHP5YXMFATLYE2EGODMPMWU6ZWO5VI6L for CICOA
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String inputLine;
            String output = "";
            while ((inputLine = in.readLine()) != null) {
                output += inputLine + "\n";
            }
            in.close();
            System.out.println(output);
        } catch (MalformedURLException e) {
            // new URL() failed
            // ...
        } catch (IOException e) {
            // openConnection() failed
            // ...
        }
    }

    private static String newApp(String appName) { // need a starter/default entity
        String url = "https://api.wit.ai/apps?v=20170307";
        String json = "{\"name\":\"" + appName + "\", \"lang\":\"en\", \"private\":\"false\"}";

        String rs = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Authorization", "Bearer 3N5XVKWA22JBA3Q7S46SEKUUNN3JFWAV");
            httpPost.setHeader("Content-Type",  "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("Response code: " + response.getStatusLine().getStatusCode());
            HttpEntity responseEntity = response.getEntity();

            // Read the contents of an entity and return it as a String.
            rs = EntityUtils.toString(responseEntity);
            System.out.println(rs);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private static void addEntity(String entityName, String token) {
        String url = "https://api.wit.ai/entities?v=20170307";
        String json = "{\"id\":\"" + entityName + "\", \"lookups\":[\"trait\"]}";
        //System.out.println(json);

        witProcess(url, json, token);
    }

    private static void trainTopicEntity(String entityName, String text, String value, String token) {
        String url = "https://api.wit.ai/entities/" + entityName + "/values?v=20170307";
        String json = "{\"value\":\"" + value + "\", \"expressions\":[\"" + text + "\", \"" + text.replaceAll("_", " ") + "\"]}";
        //System.out.println(json);

        witProcess(url, json, token);
    }

    private static void addWitValue(String entityName, String value, String token) {
        String url = "https://api.wit.ai/entities/" + entityName + "/values?v=20170307";
        String json = "{\"value\":\"" + value + "\", \"expressions\":[]}";
        //System.out.println(json);

        witProcess(url, json, token);
    }

    private static void addWitExpression(String entityName, String text, String value, String token) {
        String url = "https://api.wit.ai/entities/" + entityName + "/values/" + value + "/expressions?v=20170307";
        String json = "{\"expression\":\"" + text + "\"}";
        //System.out.println(json);

        witProcess(url, json, token);
    }

    private static void witProcess(String url, String json, String token) {
        String rs = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Authorization", "Bearer " + token);
            httpPost.setHeader("Content-Type",  "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("Response code: " + response.getStatusLine().getStatusCode());
            HttpEntity responseEntity = response.getEntity();

            // Read the contents of an entity and return it as a String.
            rs = EntityUtils.toString(responseEntity);
            System.out.println(rs);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
