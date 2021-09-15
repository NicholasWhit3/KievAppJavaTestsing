package PageObjects;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;


import java.security.DigestException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class QRTicketPageObject
{
    private static WebDriver _driver;
    private final SimpleDateFormat _formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private final Date _date = new Date(System.currentTimeMillis());
    private final LoginPageObject _loginPageObject = new LoginPageObject(_driver);

    private final By _kServices = (By.id("com.kyivdigital.test:id/iv_service"));
    private final By _qrTicketsFromServices = (By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[1]/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.ImageView"));
    private final By _geoPermission = (By.id("com.android.permissioncontroller:id/permission_allow_one_time_button"));
    private final By _qrTicketsSetAmount = (By.id("com.kyivdigital.test:id/et_amount_travel"));
    private final By _bankCardSelection = (By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[3]/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.widget.TextView[2]"));
    private final By _buttonPurchaseTicketBuying = (By.id("com.kyivdigital.test:id/btn_purchase"));


    public QRTicketPageObject(WebDriver webDriver)
    {
        _driver = webDriver;
    }
    public void SwipeScreen(Direction dir)
    {
        System.out.println("swipeScreen(): dir: '" + dir + "'"); // always log your actions

        // Animation default time:
        final int ANIMATION_TIME = 100; // ms

        final int PRESS_TIME = 100; // ms

        int edgeBorder = 1; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;

        // init screen variables
        Dimension dims = _driver.manage().window().getSize();

        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);

        switch (dir)
        {
            case DOWN: // center of footer
                pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
                break;
            case UP: // center of header
                pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
                break;
            case LEFT: // center of left side
                pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
                break;
            case RIGHT: // center of right side
                pointOptionEnd = PointOption.point(dims.width - edgeBorder, dims.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }

        // execute swipe using TouchAction
        try {
            TouchAction touchAction = new TouchAction((PerformsTouchActions) _driver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }
    public enum Direction
    {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }
    public static void ScrollToId(AndroidDriver<MobileElement> driver, String id)
    {
        MobileElement element = (MobileElement) driver.findElementByAndroidUIAutomator(
                "new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().resourceIdMatches(\"" + id + "\"));");
    }




    public void BuyingQR()
    {
        _driver.findElement(_kServices).click();
        _driver.findElement(_qrTicketsFromServices).click();
        try
        {
            _driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            _driver.findElement(_geoPermission).click();
        }catch (Exception exception)
        {
            String str = "Geolocation has been not found\n" + exception.getMessage();
            _loginPageObject.SaveLogs(str);
        }
        finally
        {
            try
            {
                    _driver.findElement(MobileBy.AndroidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true)).flingForward()"));
            }catch (Exception exception)
            {
                String str = "Swiping func didn't work\n" + exception.getMessage();
                _loginPageObject.SaveLogs(str);
            }
            _driver.findElement(_qrTicketsSetAmount).click();
            _driver.findElement(_qrTicketsSetAmount).sendKeys("2");
            ((AndroidDriver<MobileElement>) _driver).hideKeyboard();


            try
            {
                _driver.findElement(_bankCardSelection).click();
            }catch (Exception exception)
            {
                String str = "Can't choose the bank card\n" + exception.getMessage();
                _loginPageObject.SaveLogs(str);
            }
            ScrollToId((AndroidDriver<MobileElement>) _driver,"com.kyivdigital.test:id/btn_purchase");
            _driver.findElement(_buttonPurchaseTicketBuying).click();
        }


        String str = _formatter.format(_date) + " Test Buying QR tickets is completed";
        _loginPageObject.SaveLogs(str);
    }

}

