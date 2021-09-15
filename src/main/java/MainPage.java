import PageObjects.LoginPageObject;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;

public class MainPage
{
    public static void main(String args[]) throws IOException
    {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        desiredCapabilities.setCapability("platformName","android");
        desiredCapabilities.setCapability("appPackage", "com.kyivdigital.test");
        desiredCapabilities.setCapability("appActivity","com.kyivdigital.ui.main.MainActivity");

        AndroidDriver<AndroidElement> androidDriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
        LoginPageObject testStarting = new LoginPageObject(androidDriver);

        testStarting.LogInTestAccount()
                .BuyingQR();

//        androidDriver.closeApp();
//        androidDriver.quit();

    }

}
