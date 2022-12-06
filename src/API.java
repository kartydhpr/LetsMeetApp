import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import com.teamdev.jxbrowser.browser.*;
import com.teamdev.jxbrowser.engine.*;
import com.teamdev.jxbrowser.view.swing.BrowserView;

public class API {
    public BrowserView startApi(String x) {
        EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                                             .licenseKey("1BNDHFSC1G4NAQRP1IYZKNRZQVQ9BM4B4LAIAAP1B372Q9G6M90TEXR5X4HPLLCFZ28BCB")
                                             .build();
        Engine engine = Engine.newInstance(options);
        Browser browser = engine.newBrowser();
        BrowserView view = BrowserView.newInstance(browser);
        browser.navigation().loadUrl(x);
        return view;
    }
}