package RunApplication;

import ApplicationsConfirgurations.He.HeFacility;
import ApplicationsConfirgurations.He.HeLogin;
import Payload.He.HePayload;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HotelExtranetApiTestingTest {
    public HeLogin httpLogin;
    public HeFacility facility;
    public HePayload payload;
    private String sessionID;

    @BeforeEach
    void setUp() throws Exception {
        httpLogin = new HeLogin();
        facility = new HeFacility();
        payload = new HePayload();

        //Log-in in HE and get the Session ID
        String usernameAndPassword = payload.heLoginPayload();
        this.sessionID = httpLogin.loginToHotelierExtranet(usernameAndPassword);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void facility() throws Exception {
        // Passing session ID to manipulate data in HE

        System.out.println(sessionID + "facility");
        String facility_name = payload.heFacility();
        String facility_id = facility.addFacility(sessionID, facility_name);
        Assert.assertNotNull(facility_id);


        //List of Facility
        System.out.println("Test: " + facility_id);
        String[] getAllFacility1 = facility.getAllFacility(sessionID);
        Boolean result1 = Arrays.asList(getAllFacility1).contains(facility_id.replace("facility_id=", ""));
        assertEquals(result1, true);

//        Delete facility ID
        facility.deleteFacility(sessionID, facility_id);

        System.out.println("Test: " + facility_id);
        String[] getAllFacility2 = facility.getAllFacility(sessionID);
        Boolean result2 = Arrays.asList(getAllFacility2).contains(facility_id.replace("facility_id=", ""));
        assertEquals(result2, false);
    }

}