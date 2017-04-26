package AplicationsConfirgurations.He;

import HttpConnection.HttpConnection;
import payload.He.HeUrl;
import payload.He.HeUrlParameters;

import javax.net.ssl.HttpsURLConnection;

public class HeLogin {
    HttpConnection con;
    HeUrlParameters getData;
    HeUrl getHeUrl;

    public HeLogin(){
        con = new HttpConnection();
        getHeUrl = new HeUrl();
        getData = new HeUrlParameters();
    }

    public String loginToHotelierExtranet() throws Exception {

        // Send POST Request
        String heLogInUrl = getHeUrl.HeLoginUrl();
        HttpsURLConnection connection = con.httpHeConnection(heLogInUrl);

        // Sending Data to Server
        String sendData = getData.heLoginPayload();
        con.sendParameters(connection,sendData, null);

        // Display and Check Response
        con.displayResponse(connection, heLogInUrl, sendData);

        // Getting the Session ID to manipuluate data in HE
        String cookieValue = con.getCookie(connection);
        return cookieValue;
    }
}

