package ApplicationsConfirgurations.He;

import javax.net.ssl.HttpsURLConnection;
import HttpConnection.HttpConnection;
import Payload.He.HePayload;
import Payload.He.HeUrl;

public class HeLogin {
    HttpConnection con;
    HePayload getData;
    HeUrl getHeUrl;

    public HeLogin(){
        con = new HttpConnection();
        getHeUrl = new HeUrl();
        getData = new HePayload();
    }

    public String loginToHotelierExtranet(String usernameAndPassword) throws Exception {

        // Send POST Request
        String httpRequest = "POST";
        String heLogInUrl = getHeUrl.HeLoginUrl();
        HttpsURLConnection connection = con.httpHeConnection(heLogInUrl);

        // Sending Data to Server
        con.sendParameters(connection,usernameAndPassword, null, httpRequest);

        // Display and Check Response
        con.displayResponse(connection, heLogInUrl, usernameAndPassword, httpRequest);

        // Getting the Session ID to manipuluate data in HE
        String cookieValue = con.getCookie(connection);
        return cookieValue;
    }
}

