
import com.assabetsecurity.core.tools.ebay.*;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.logging.Logger;

public class FetchEbayTest {

    Logger log = Logger.getLogger(EBayConnector.class.getName());

    @Test
    public void getQueryString() throws IOException {

        EBayConnector connector = new EBayConnector("550");
        connector.getUltimateQueryString(1);
        log.info("You can click the above link to get the content in browser");

    }




}
