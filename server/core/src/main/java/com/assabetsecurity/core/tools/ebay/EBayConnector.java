package com.assabetsecurity.core.tools.ebay;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;



/**
 * Created by george on 1/3/16.
 */
public class EBayConnector {

    final static Logger log = LoggerFactory.getLogger(EBayConnector.class.getName());

    private final static String findingServiceUrl = "http://svcs.ebay.com/services/search/FindingService/v1";
    private final String descriptionHTML = "http://vi.vipr.ebaydesc.com/ws/eBayISAPI.dll?item=";
    private String categoryId;
    private String prefixQueryString;

    private static String appName= "BazilTer-bfa5-437f-84a0-bc6bf2582826";

    private static String getFindingServiceUrl(int categoryId) {
        return findingServiceUrl  + "?"
                +  "SECURITY-APPNAME"          +"="+ appName
                + "&"  +  "SERVICE-VERSION"        +"="+ "1.13.0"
                + "&"  +  "GLOBAL-ID"             +"="+ "EBAY-US"
                + "&"  + "SERVICE-NAME"           +"="+ "FindingService"
                + "&"  +  "OPERATION-NAME"         +"="+ "findItemsByCategory"
                + "&"  +  "RESPONSE-DATA-FORMAT"  +"="+ "XML"
                + "&"  +  "categoryId"            +"="+ String.valueOf(categoryId)
                + "&"  + "sortOrder=StartTimeNewest";
    }

/*
    public EBayConnector(String categoryId) {
        this.categoryId = categoryId;
        this.prefixQueryString = findingServiceUrl  + "?"
                +  "SECURITY-APPNAME"          +"="+ appName
                + "&"  +  "SERVICE-VERSION"        +"="+ "1.13.0"
                + "&"  +  "GLOBAL-ID"             +"="+ "EBAY-US"
                + "&"  + "SERVICE-NAME"           +"="+ "FindingService"
                + "&"  +  "OPERATION-NAME"         +"="+ "findItemsByCategory"
                + "&"  +  "RESPONSE-DATA-FORMAT"  +"="+ "XML"
                + "&"  +  "categoryId"            +"="+ this.categoryId;
    }
*/


    public String getUltimateQueryString(int categoryId,int page){
           log.info(getFindingServiceUrl(categoryId) + "&pageNumber=" + page);
        return      getFindingServiceUrl(categoryId) + "&pageNumber=" + page;
    }


    HttpClient client = HttpClients.createDefault();

    public String getItemDetails(String id) {

        String baseUrl = "http://open.api.ebay.com/shopping?" +
                "callname=GetSingleItem&responseencoding=XML&siteid=0&version=949" +
                "&appid="+appName+"&IncludeSelector=Description" +
                "&ItemID="+id;


        HttpGet httpget = new HttpGet(baseUrl);
        log.info(httpget.getURI().toString());
        try {
            HttpResponse response = client.execute(httpget);
            if(response.getStatusLine().getStatusCode()==200) {
                StringBuilder sb = new StringBuilder();
                String inputLine;
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                br.close();
                return sb.toString();
            }
        } catch (IOException e) {
            log.error("", e);
        }
        return "";
    }
    //http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=XML&appid=BazilTer-bfa5-437f-84a0-bc6bf2582826&siteid=0&version=515&ItemID=252232453072&IncludeSelector=Description
    //http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=XML&appid=BazilTer-bfa5-437f-84a0-bc6bf2582826&siteid=0&version=515&ItemID=252232453072
    //http://svcs.ebay.com/shopping?callname=GetSingleItem&responseencoding=XML&appid=YourAppIDHere&siteid=0&version=661&IncludeSelector=Description,ItemSpecifics,ShippingCosts&ItemID=110044494992

    public static class EBayItemsIterator implements  Iterator<String> {
        String url;
        int page = 1;
        HttpClient client = null;
        boolean isLastPage = false;
        String sortOrder = null;
        public EBayItemsIterator(int categoryId) {
            this(categoryId, null);
        }

        public EBayItemsIterator(int categoryId, String sortOrder) {
            this.url = getFindingServiceUrl(categoryId);
            this.client = HttpClients.createDefault();
            this.sortOrder = sortOrder;
        }

        @Override
        public boolean hasNext() {
            return !isLastPage;
        }

        @Override
        public String next() {
            if(isLastPage) return "";
            try {
                HttpGet httpget = new HttpGet(url+"&paginationInput.entriesPerPage=100&paginationInput.pageNumber="+String.valueOf(page));
                log.info(httpget.getURI().toString());
                page += 1;

                HttpResponse response = client.execute(httpget);
                if(response.getStatusLine().getStatusCode()==200) {
                    StringBuilder sb = new StringBuilder();
                    String inputLine;
                    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    br.close();

                    String ret = sb.toString();
                    if(ret.contains("<ack>Failure</ack>")) {
                        isLastPage = true;
                    }
                    return ret;
                } else {
                    return "";
                }
            } catch (Exception e) {
                isLastPage = true;
                log.error("", e);
            }
            return "";
        }

        @Override
        public void remove() {

        }
    }
}
