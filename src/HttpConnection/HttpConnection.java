package HttpConnection;

import Payload.He.HeUrlParameters;
import Payload.He.HeUrl;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpConnection {
    HeUrl getHeUrl;
    HeUrlParameters getData;

    public HttpConnection(){
        getHeUrl = new HeUrl();
        getData = new HeUrlParameters();
    }

    public HttpsURLConnection httpHeConnection(String setUrl) throws IOException {
        URL obj = new URL(setUrl);
        HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
        return connection;
    }

    public void sendParameters(HttpsURLConnection connection, String urlParameters, String session) throws IOException {

        connection.setRequestProperty("Cookie", session);
        connection.setDoOutput(true);
        DataOutputStream sendPayLoad = new DataOutputStream(connection.getOutputStream());
        sendPayLoad.writeBytes(urlParameters);
        sendPayLoad.flush();
        sendPayLoad.close();
    }

    public void displayResponse(HttpsURLConnection connection, String url, String payload) throws IOException {

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + payload);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print result
        System.out.println(response.toString());
    }

    public String getCookie(HttpsURLConnection connection){
        Map<String, List<String>> RequestHeaderFields = connection.getHeaderFields();

        String cookieSessionValue = null;
        for (Map.Entry<String, List<String>> listOfRequestHeaderFields : RequestHeaderFields.entrySet()) {

            if ("Set-Cookie".equalsIgnoreCase(listOfRequestHeaderFields.getKey())) {
                String[] phpSessionID = listOfRequestHeaderFields.getValue().toString().split(";\\s*");
                String cookieValue = phpSessionID[0];
                if (cookieValue.indexOf("=") != -1) {
                    cookieSessionValue = cookieValue.replaceAll("\\[", "");
                }
            }
        }
        return cookieSessionValue;
    }
}
