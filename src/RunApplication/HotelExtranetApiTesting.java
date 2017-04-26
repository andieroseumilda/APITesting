package RunApplication;

import ApplicationsConfirgurations.He.HeFacility;
import ApplicationsConfirgurations.He.HeLogin;

public class HotelExtranetApiTesting {

    public static void main(String[] args) throws Exception {

        HeLogin httpLogin = new HeLogin();
        HeFacility facility = new HeFacility();

        //Log-in in HE and get the Session ID
        String sessionID = httpLogin.loginToHotelierExtranet();

        // Passing session ID to manipulate data in HE
        facility.addFacility(sessionID);
    }

}