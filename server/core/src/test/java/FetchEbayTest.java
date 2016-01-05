
import com.assabetsecurity.core.tools.ebay.*;


import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.logging.Logger;

public class FetchEbayTest {

    Logger log = Logger.getLogger(FetchEbayTest.class.getName());


    // category 550, page 1
    @Test
    public void getQueryString() throws IOException {

        EBayConnector firstTest = new EBayConnector();
        //String ultimateRequest = firstTest.getUltimateQueryStringForPage(1);
        //log.info(ultimateRequest);
        //log.info("You can click the above link to get the content in browser");
    }


    // returns the request as an unordered string
    @Test
    public void getResponseAsStringForTheFirstPage() throws IOException {
/*
        EBayRequest secondTest = new EBayRequest("550");
        String ultimateRequest = secondTest.getUltimateQueryStringForPage(1);

        EBayResponse eBayResponse = new EBayResponse(ultimateRequest);
        eBayResponse.getAsString();*/
    }


    //  returns the request as an XML  structure
    // 3 tags -  itemId, name, link to it's description
    @Test
    public void getResponseAsXMLForTheFirstPage() throws IOException, ParserConfigurationException, SAXException {

    /*    EBayRequest secondTest = new EBayRequest("550");
        String ultimateRequest = secondTest.getUltimateQueryStringForPage(1);

        EBayResponse eBayResponse = new EBayResponse(ultimateRequest);
        log.info("As XML");
        eBayResponse.getAsXML();*/
    }


    //  returns the request as an XML  structure
    // 3 tags -  itemId, name, full HTML description content instead of link to it's description
    @Test
    public void getResponseWithHTML() throws IOException, ParserConfigurationException, SAXException {
/*
        EBayRequest secondTest = new EBayRequest("550");
        String ultimateRequest = secondTest.getUltimateQueryStringForPage(1);

        EBayResponse eBayResponse = new EBayResponse(ultimateRequest);
        log.info("Response is XML structure with HTML content as a description");
        eBayResponse.getAsXMLWithHTML();*/
    }

}
