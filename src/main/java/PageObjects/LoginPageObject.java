package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoginPageObject
{
    private static WebDriver _driver;
    private final SimpleDateFormat _formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private final Date _date = new Date(System.currentTimeMillis());
    private final Logger _logger = Logger.getLogger("Logs");
    private FileHandler _fileHandler;


    private final String _phoneNumber = "123456789";
    private final String _firstSmsNumber = "2";
    private final String _secondSmsNumber = "8";
    private final String _thirdSmsNumber = "1";
    private final String _fourthSmsNumber = "2";


    private final By _phoneNumberSelector = (By.id("com.kyivdigital.test:id/et_phone_number"));
    private final By _buttonGettingSMSSelector = (By.id("com.kyivdigital.test:id/tv_send"));
    private final By _fistSmsField = (By.id("com.kyivdigital.test:id/et_code_1"));
    private final By _secondSmsField = (By.id("com.kyivdigital.test:id/et_code_2"));
    private final By _thirdSmsField = (By.id("com.kyivdigital.test:id/et_code_3"));
    private final By _fourthSmsField = (By.id("com.kyivdigital.test:id/et_code_4"));
    private final By _closeFeedField = (By.id("com.kyivdigital.test:id/btn_accessibly"));

    public LoginPageObject(WebDriver webDriver)
    {
        _driver = webDriver;
    }

    public void SaveLogs(String str)
    {
        try
        {
            _fileHandler = new FileHandler("C:/Users/Nicho/IdeaProjects/KievAppJavaTestsing/Logs.txt");
            _logger.addHandler(_fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            _fileHandler.setFormatter(formatter);

            _logger.info(str);
        }catch (SecurityException | IOException e){
                e.printStackTrace();
        }
    }

    public QRTicketPageObject LogInTestAccount()
    {
        _driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        _driver.findElement(_phoneNumberSelector).sendKeys(_phoneNumber);
        _driver.findElement(_buttonGettingSMSSelector).click();

        _driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        _driver.findElement(_fistSmsField).sendKeys(_firstSmsNumber);
        _driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        _driver.findElement(_secondSmsField).sendKeys(_secondSmsNumber);
        _driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        _driver.findElement(_thirdSmsField).sendKeys(_thirdSmsNumber);
        _driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        _driver.findElement(_fourthSmsField).sendKeys(_fourthSmsNumber);

        _driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        _driver.findElement(_closeFeedField).click();

        String str = _formatter.format(_date) + " Testcase Login is completed";
        SaveLogs(str);

        return new QRTicketPageObject(_driver);
    }
}
