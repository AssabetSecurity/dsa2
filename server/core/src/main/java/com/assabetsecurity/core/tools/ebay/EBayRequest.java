package com.assabetsecurity.core.tools.ebay;

import java.io.IOException;
import java.util.logging.Logger;


/**
 * Created by george on 1/3/16.
 */
public class EBayRequest {


    public EBayRequest(String categoryId) {
        this.categoryId = categoryId;
    }

    Logger log = Logger.getLogger(EBayRequest.class.getName());

    private final String url = "http://svcs.ebay.com/services/search/FindingService/v1";
    private final String descriptionHTML = "http://vi.vipr.ebaydesc.com/ws/eBayISAPI.dll?item=";
    private String categoryId;

    private boolean lastPage = false;

    public String getPrefix(String categoryId) {
        this.categoryId = categoryId;
         String prefix = url + "?"
                +  "SECURITY-APPNAME"          +"="+ "BazilTer-bfa5-437f-84a0-bc6bf2582826"
                + "&"  +  "SERVICE-VERSION"        +"="+ "1.13.0"
                + "&"  +  "GLOBAL-ID"             +"="+ "EBAY-US"
                + "&"  + "SERVICE-NAME"           +"="+ "FindingService"
                + "&"  +  "OPERATION-NAME"         +"="+ "findItemsByCategory"
                + "&"  +  "RESPONSE-DATA-FORMAT"  +"="+ "XML"
                + "&"  +  "categoryId"            +"="+ this.categoryId;
        return prefix;
    }


    public String getUltimateQueryStringForPage(int page){
         String out = getPrefix(categoryId) + "&pageNumber=" + page;
         log.info(out);
        return  out;
    }

    public String getResponseString(int page) throws IOException {



        return "";

    }





}
