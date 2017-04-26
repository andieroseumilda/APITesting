package AplicationsConfirgurations.He;

import HttpConnection.HttpConnection;
import payload.He.HeUrl;
import payload.He.HeUrlParameters;

import javax.net.ssl.HttpsURLConnection;

public class HeFacility {
    HttpConnection con;
    HeUrlParameters getData;
    HeUrl getHeUrl;

    public HeFacility(){
        con = new HttpConnection();
        getHeUrl = new HeUrl();
        getData = new HeUrlParameters();
    }


    public void addFacility(String session) throws Exception {

        // Send POST Request
        String url = getHeUrl.HeFacilityName();
        HttpsURLConnection connection = con.httpHeConnection(url);

        String facility_payload = getData.heFacility();
        con.sendParameters(connection, facility_payload, session);

        // Display and Check Response
        con.displayResponse(connection, url, facility_payload);
    }

}
