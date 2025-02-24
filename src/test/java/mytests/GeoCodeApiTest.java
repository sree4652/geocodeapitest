package mytests;

import org.example.utils.GeoCodeApiUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

public class GeoCodeApiTest {
    GeoCodeApiUtil geoCodeApiUtil = new GeoCodeApiUtil();
    Map<String, Object> responseMap;

    @Test(groups = {"regression"},description = "This test verifies valid US zipcode returns a valid lat, long")
    public void verifySingleZipCodeWithInUSA() {

        responseMap = geoCodeApiUtil.getCoordinatesByZipCode("48864");
        Assert.assertNotNull(responseMap);
        Assert.assertEquals(responseMap.get("latitude").toString(),"42.7053");
        Assert.assertEquals(responseMap.get("longitude").toString(),"-84.4187");
        Assert.assertEquals(responseMap.get("name").toString().toLowerCase(),"okemos");

    }

    @Test(groups = {"regression"},description = "This test verifies single valid US city name returns a valid lat, long")
    public void verifySingleCityWithInUSA() {

        responseMap = geoCodeApiUtil.getCoordinatesByLocation("madison");
        Assert.assertNotNull(responseMap);
        Assert.assertEquals(responseMap.get("latitude").toString(),"43.074761");
        Assert.assertEquals(responseMap.get("longitude").toString(),"-89.3837613");
        Assert.assertEquals(responseMap.get("name").toString().toLowerCase(),"madison");
        Assert.assertEquals(responseMap.get("state").toString().toLowerCase(),"wisconsin");

    }
    @Test(groups = {"regression"},description = "This test verifies valid  US city and state name combination as input and returns a valid lat, long ")
    public void verifyCityAndStateCombinationWithInUSA() {

        responseMap = geoCodeApiUtil.getCoordinatesByLocation("“Madison, WI”");
        Assert.assertNotNull(responseMap);
        Assert.assertEquals(responseMap.get("latitude").toString(),"43.074761");
        Assert.assertEquals(responseMap.get("longitude").toString(),"-89.3837613");
        Assert.assertEquals(responseMap.get("name").toString().toLowerCase(),"madison");
        Assert.assertEquals(responseMap.get("state").toString().toLowerCase(),"wisconsin");

    }
    @Test(groups = {"regression"},description = "This test verifies a city outside usa as input and returns no data")
    public void verifyCityOutsideUSA() {

        responseMap = geoCodeApiUtil.getCoordinatesByLocation("“London, UK”");
        Assert.assertNull(responseMap);

    }
    @Test(groups = {"regression"},description = "This test verifies a zipcode outside usa as input and returns no data")
    public void verifyZipCodeOutsideUSA() {

        responseMap = geoCodeApiUtil.getCoordinatesByZipCode("L18JQ");
        Assert.assertNull(responseMap);

    }
}
