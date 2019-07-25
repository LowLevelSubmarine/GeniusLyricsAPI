package core;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    public HttpURLConnection getConnection(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", Statics.USER_AGENT);
        con.setRequestProperty("accept-language", "en-US");
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Accept","application/json");
        return con;
    }

    public String executeGet(HttpURLConnection con) {
        try {
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String executePost(HttpURLConnection con, String content) {
        try {
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            byte[] postData = content.getBytes();
            wr.write(postData);
            //wr.flush();
            //wr.close();
            con.connect();
            int responseCode = con.getResponseCode();
            if(responseCode == HttpsURLConnection.HTTP_OK){
                return readStream(con.getInputStream());
            }else{
                return readStream(con.getErrorStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}
