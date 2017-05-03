package ApplicationsConfirgurations.He;

import HttpConnection.HttpConnection;
import Payload.He.HePayload;
import Payload.He.HeUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;

public class HeFacility {
    private HttpConnection con;
    private HePayload getData;
    private HeUrl getHeUrl;

    public HeFacility(){
        con = new HttpConnection();
        getHeUrl = new HeUrl();
        getData = new HePayload();
    }

    public HttpsURLConnection sendPostRequest(String heUrl, String getPayload, String session, String httpRequest) throws IOException, JSONException {
        //Send Post Request
        String url = heUrl ;
        HttpsURLConnection connection = con.httpHeConnection(url);

        //Get payload
        String payload = getPayload;
        con.sendParameters(connection,payload,session,httpRequest);
        con.displayResponse(connection, url, payload, httpRequest);

        return connection;
    }



    public String addFacility(String session, String facility_payload) throws Exception {
        HttpsURLConnection httpCon = sendPostRequest(getHeUrl.HeFacilityName(), getData.heFacility(),session,"POST");
        return getFacilityId(httpCon);
    }

    public void deleteFacility(String session, String facility_id) throws IOException, JSONException {
        sendPostRequest(getHeUrl.deleteFacility(), facility_id,session,"POST");
    }

    public String[] getAllFacility(String session) throws IOException, JSONException {
        HttpsURLConnection httpCon = sendPostRequest(getHeUrl.listOfFacility(), null,session,"GET");

        String getResponse = con.jsonFormat(httpCon);
        JSONObject jsonObj = new JSONObject(getResponse);

        if (jsonObj.has("data")){
            System.out.println(jsonObj.has("data"));
        }
        JSONArray array = (JSONArray) jsonObj.get("data");

        String[] getAllFacility = new String[array.length()];
        for(int i = 0 ; i < array.length() ; i++){
            String getAllFacilityID = array.getJSONObject(i).getString("facility_cd");
            getAllFacility[i] = getAllFacilityID;
        }
        System.out.println(getAllFacility);
        return getAllFacility;
    }

    public String getFacilityId(HttpsURLConnection connection) throws JSONException, IOException {
        String getResponse = con.jsonFormat(connection);
        JSONObject jsonObj = new JSONObject(getResponse);
        String facility_id = null;

        if (jsonObj.has("facility_cd")){
            facility_id = jsonObj.get("facility_cd").toString();
        }
        String jsonFacilityId = "facility_id="+facility_id;
        return jsonFacilityId;
    }


}
