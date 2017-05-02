package RunApplication;

import ApplicationsConfirgurations.He.HeFacility;
import ApplicationsConfirgurations.He.HeLogin;
import Payload.He.HePayload;

public class HotelExtranetApiTesting {

    public static void main(String[] args) throws Exception {

        HeLogin httpLogin = new HeLogin();
        HeFacility facility = new HeFacility();
        HePayload payload = new HePayload();

        //Log-in in HE and get the Session ID
        String usernameAndPassword = payload.heLoginPayload();
        String sessionID = httpLogin.loginToHotelierExtranet(usernameAndPassword);

        // Passing session ID to manipulate data in HE
        String facility_name = payload.heFacility();
        String facility_id = facility.addFacility(sessionID, facility_name);

        //Delete facility ID
        facility.deleteFacility(sessionID, facility_id);
    }

}