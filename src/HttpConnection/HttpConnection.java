package HttpConnection;

import Payload.He.HePayload;
import Payload.He.HeUrl;
import org.json.JSONException;

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
    HePayload getData;

    public HttpConnection(){
        getHeUrl = new HeUrl();
        getData = new HePayload();
    }

    public HttpsURLConnection httpHeConnection(String setUrl) throws IOException {
        URL obj = new URL(setUrl);
        HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
        return connection;
    }

    public void sendParameters(HttpsURLConnection connection, String urlParameters, String session, String httpRequest) throws IOException {
        connection.setRequestProperty("Cookie", session);
        connection.setDoOutput(true);
        DataOutputStream sendPayLoad = new DataOutputStream(connection.getOutputStream());

        if(httpRequest == "POST") {
            sendPayLoad.writeBytes(urlParameters);
            sendPayLoad.flush();
        }
        sendPayLoad.close();
    }

    public void displayResponse(HttpsURLConnection connection, String url, String payload, String httpRequest) throws IOException, JSONException {
        int responseCode = connection.getResponseCode();
        System.out.println("\nSending request to URL : " + url);
        if(httpRequest.equalsIgnoreCase("POST")) {
            System.out.println("Post parameters : " + payload);
        }
        System.out.println("Response Code : " + responseCode);
    }

    public void getDisplayResponse(HttpsURLConnection connection, String url) throws IOException, JSONException {
        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
    }

    public String jsonFormat(HttpsURLConnection connection) throws IOException, JSONException {
        StringBuffer response;
        String getResponse;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        //print result
        getResponse = response.toString();
//        System.out.println(getResponse);
        return  getResponse;
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
