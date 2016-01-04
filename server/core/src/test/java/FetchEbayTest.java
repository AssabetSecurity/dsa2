
import com.assabetsecurity.core.tools.ebay.*;


import org.junit.Test;

import java.io.IOException;
import java.util.logging.Logger;

public class FetchEbayTest {

    Logger log = Logger.getLogger(EBayRequest.class.getName());

    @Test
    public void getQueryString() throws IOException {

        EBayRequest connector = new EBayRequest("550");
        String ultimateRequest = connector.getUltimateQueryStringForPage(1);
        log.info(ultimateRequest);
        log.info("You can click the above link to get the content in browser");

    }




}
