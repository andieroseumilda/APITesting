package ApplicationsConfirgurations.He;

import HttpConnection.HttpConnection;
import Payload.He.HePayload;
import Payload.He.HeUrl;
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


    public String addFacility(String session, String facility_payload) throws Exception {
        // Send POST Request
        String url;
        url = getHeUrl.HeFacilityName();
        HttpsURLConnection connection = con.httpHeConnection(url);

        facility_payload = getData.heFacility();
        con.sendParameters(connection, facility_payload, session);

        // Display and Check Response
        con.displayResponse(connection, url, facility_payload);
        
        String getJsonFormat;
        getJsonFormat = getFacilityId(connection);

        return getJsonFormat;
    }

    public void deleteFacility(String session, String getFacilityIdInResponse) throws IOException, JSONException {
        // Send Post Request
        String url = getHeUrl.deleteFacility();
        HttpsURLConnection connection = con.httpHeConnection(url);

        String facility_id = getFacilityIdInResponse;
        System.out.println(facility_id);
        con.sendParameters(connection, facility_id, session);
        con.displayResponse(connection, url, facility_id);
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
