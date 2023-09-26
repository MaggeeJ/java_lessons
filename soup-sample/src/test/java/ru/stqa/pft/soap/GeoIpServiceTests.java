package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {
        String geoIp =new GeoIPService().getGeoIPServiceSoap12().getIpLocation("95.105.68.245");
        assertEquals(geoIp,"<GeoIP><Country>RU</Country><State>08</State></GeoIP>");
    }

    @Test
    public void testInvalidIp() {
        String geoIp = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("блаблабла");
        assertEquals(geoIp,"<GeoIP><Country>RU</Country><State>08</State></GeoIP>");
    }

}
