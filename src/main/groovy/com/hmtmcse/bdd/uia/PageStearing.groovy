package com.hmtmcse.bdd.uia

import net.serenitybdd.core.pages.PageObject
import com.google.common.base.Function;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat

class PageSteering extends PageObject {

    public static WebDriver webDriver;

    private static void init(String driverType) {
        if (driverType.equals("chrome")) {
//            System.setProperty(SystemConstant.CHROME_DRIVER, ConfigLoader.getPropertiesById(SystemConstant.LOCAL_CHROME_DRIVER_LOCATION, SystemConstant.CHROME_DRIVER));
        } else if (driverType.equals("firefox")) {
//            System.setProperty(SystemConstant.GECKO_DRIVER, ConfigLoader.getPropertiesById(SystemConstant.LOCAL_GECKO_DRIVER_LOCATION, SystemConstant.GECKO_DRIVER));
        }
    }

    public static void setup() {
        final String driverType = "" // ConfigLoader.getPropertiesById(SystemConstant.DRIVER_TYPE);
        init(driverType);

        String isRemote = "" // ConfigLoader.getPropertiesById(SystemConstant.IS_REMOTE, "no");


        if (isRemote.equals("yes")) {
            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setJavascriptEnabled(true);
                capabilities.setBrowserName(driverType);
                capabilities.setPlatform(Platform.ANY);
//                webDriver = new RemoteWebDriver(new URL(ConfigLoader.getPropertiesById(SystemConstant.REMOTE_BROWSER_URL, "no")), capabilities);
                ((RemoteWebDriver) webDriver).setFileDetector(new LocalFileDetector());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            if (driverType.equals("chrome")) {
                webDriver = new ChromeDriver();
            } else if (driverType.equals("firefox")) {
                webDriver = new FirefoxDriver();
            }
        }
    }

    public static void exit() {
        webDriver.quit();
    }

    public void close() {
        webDriver.close();
    }


    public WebElement find(String elementName, String userMessage) {
        if (elementName == null || elementName.equals("")) {
            return null;
        } else {
            return webDriver.findElement(getBy(elementName, userMessage));
        }
    }

    public WebElement find(String elementName) {
        return find(elementName, SystemConstant.ELEMENT_MESSAGE);
    }

    public WebElement find(WebElement webElement, String elementName, String userMessage) {
        if (elementName == null || elementName.equals("")) {
            return webElement;
        } else {
            return webElement.findElement(getBy(elementName, userMessage));
        }
    }

    public WebElement find(WebElement webElement, String elementName) {
        return find(webElement, elementName, SystemConstant.ELEMENT_MESSAGE);
    }

    private void findExceptionHandler(Exception e, String userMessage) {
        String message = e.getMessage();
        if (SystemConstant.isDebug) {
            e.printStackTrace();
        }
        if (userMessage != null || !userMessage.equals("")) {
            message = userMessage;
        }
        Assert.fail(message);
    }

    public List<WebElement> findAll(String elementName, String userMessage) {
        if (elementName == null || elementName.equals("")) {
            return null;
        } else {
            return webDriver.findElements(getBy(elementName, userMessage));
        }
    }

    public List<WebElement> findAll(String elementName) {
        return findAll(elementName, SystemConstant.ELEMENT_MESSAGE);
    }


    public List<WebElement> findAll(WebElement webElement, String elementName, String userMessage) {
        if (elementName == null || elementName.equals("")) {
            return null;
        } else {
            return webElement.findElements(getBy(elementName, userMessage));
        }
    }

    public List<WebElement> findAll(WebElement webElement, String elementName) {
        return findAll(webElement, elementName, SystemConstant.ELEMENT_MESSAGE);
    }

    public void findAndSetValue(String elementName, String value) {
        findAndSetValue(elementName, value, SystemConstant.ELEMENT_MESSAGE);
    }

    public void findAndSetValue(String elementName, String value, String message) {
        WebElement webElement = find(elementName, message);
        scrollToElement(webElement);
        webElement.sendKeys(value);
    }

    public void findAndTriggerClick(String elementName) {
        findAndTriggerClick(elementName, SystemConstant.ELEMENT_MESSAGE);
    }

    public void findAndTriggerClick(String elementName, String message) {
        WebElement webElement = find(elementName, message);
        scrollToElement(webElement);
        webElement.click();
    }

    public void findAndTriggerClick(WebElement webElement, String elementName) {
        findAndTriggerClick(webElement, elementName, SystemConstant.ELEMENT_MESSAGE);
    }

    public void findAndTriggerClick(WebElement webElement, String elementName, String message) {
        WebElement currentElement = find(webElement, elementName, message);
        scrollToElement(currentElement);
        currentElement.click();
    }

    public String findAndGetText(String elementName) {
        return findAndGetText(elementName, SystemConstant.ELEMENT_MESSAGE);
    }

    public String findAndGetText(String elementName, String message) {
        WebElement webElement = find(elementName, message);
        return webElement.getText();
    }

    public String findAndGetText(WebElement webElement, String elementName, String message) {
        WebElement currentElement = find(webElement, elementName, message);
        return currentElement.getText();
    }

    public String findAndGetText(WebElement webElement, String elementName) {
        return findAndGetText(webElement, elementName, SystemConstant.ELEMENT_MESSAGE);
    }

    public String findAndGetAttributeValue(String elementName, String attributeName) {
        return findAndGetAttributeValue(elementName, attributeName, SystemConstant.ELEMENT_MESSAGE);
    }

    public String findAndGetAttributeValue(String elementName, String attributeName, String message) {
        WebElement webElement = find(elementName, message);
        return webElement.getAttribute(attributeName);
    }

    public String findAndGetAttributeValue(WebElement webElement, String elementName, String attributeName) {
        return findAndGetAttributeValue(webElement, elementName, attributeName, SystemConstant.ELEMENT_MESSAGE);
    }

    public String findAndGetAttributeValue(WebElement webElement, String elementName, String attributeName, String message) {
        webElement = find(webElement, elementName, message);
        return webElement.getAttribute(attributeName);
    }

    public void browseURL(String url) {
        if (url != null && !url.equals("")) {
            if (webDriver == null) {
                SeleniumHelper.setup();
            }
            webDriver.manage().window().maximize();
            waitNSecond(5);
            webDriver.get(url);
        }
    }

    public void browseURL(String url, int waitTime) {
        if (url != null && !url.equals("")) {
            if (webDriver == null) {
                SeleniumHelper.setup();
            }
            webDriver.manage().window().maximize();
            waitNSecond(waitTime);
            webDriver.get(url);
        }
    }

    public void browseLocalHTML(String relativePath) {
        String htmlLocation = "file:" + System.getProperty("user.dir") + "/" + relativePath;
        webDriver.manage().window().maximize();
        waitNSecond(5);
        webDriver.get(htmlLocation);
    }

    public void browseLocalHTML(String relativePath, int waitTime) {
        String htmlLocation = "file:" + System.getProperty("user.dir") + "/" + relativePath;
        webDriver.manage().window().maximize();
        waitNSecond(waitTime);
        webDriver.get(htmlLocation);
    }

    public void sleep(double waitTime) {
        waitTime *= 1000;
        try {
            Thread.sleep((long) waitTime);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    public void pageLoadTimeout(double waitTime) {
        waitTime *= 1000;
        webDriver.manage().timeouts().pageLoadTimeout((int) waitTime, TimeUnit.MILLISECONDS);
    }

    public void waitNSecond(int n) {
        webDriver.manage().timeouts().implicitlyWait(n, SECONDS);
    }


    public WebElement fluentWait(final String elementName) {
        return fluentWait(elementName, 40, 2);
    }

    public WebElement fluentWait(final String elementName, int waitTime, int pollTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(waitTime, SECONDS)
                .pollingEvery(pollTime, SECONDS)
                .ignoring(NoSuchElementException.class, AssertionError.class);

        WebElement webElement = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return find(elementName, SystemConstant.ELEMENT_MESSAGE);
            }
        });
        return webElement;
    }

    public void isEqual(Object expected, Object actual, String message) {
        try {
            Assert.assertEquals(message, expected, actual);
        } catch (Exception ex) {
            Assert.fail(message);
        }
    }

    public String[] getValueFromList(List<WebElement> list) {
        return getValueFromList(list, "value");
    }

    public String[] getValueFromList(List<WebElement> list, String attribute) {
        List<String> value = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            WebElement currentElement = list.get(i);
            if (!(currentElement.getAttribute(attribute).equals("") || currentElement.getAttribute(attribute) == null)) {
                value.add(currentElement.getAttribute(attribute));
            }
        }
        return value.toArray(new String[value.size()]);
    }

    public void resetText(String elementName) {
        WebElement webElement = find(elementName, SystemConstant.ELEMENT_MESSAGE);
        webElement.clear();
    }

    public String findAndReplace(String string, String oldWord, String newWord) {
        return string.replace(oldWord, newWord);
    }

    public void hoverCursor(WebElement webElement) {
        Actions builder = new Actions(webDriver);
        builder.moveToElement(webElement).build().perform();
    }

    public void scrollToElement(WebElement webElement) {
        Actions actions = new Actions(webDriver);
        actions.moveToElement(webElement);
        actions.perform();
    }

    public Boolean regexSearch(String parentString, String regexPattern) {
        return parentString.matches(regexPattern);
    }

    public Boolean isStringContain(String parentString, String searchString) {
        return parentString.contains(searchString);
    }

    public By getBy(String elementName, String userMessage) {
        try {
            String genericName = elementName.substring(1);
            if (elementName.startsWith("/")) {
                return By.xpath(elementName);
            } else if (elementName.startsWith(".")) {
                return By.className(genericName);
            } else if (elementName.startsWith("#")) {
                return By.id(genericName);
            } else if (elementName.startsWith("%")) {
                return By.tagName(genericName);
            } else if (elementName.startsWith("@")) {
                return By.linkText(genericName);
            } else if (elementName.startsWith("|")) {
                return By.cssSelector(genericName);
            } else if (elementName.startsWith("*")) {
                return By.partialLinkText(genericName);
            } else {
                return By.name(elementName);
            }

        } catch (Exception e) {
            findExceptionHandler(e, userMessage);
            return null;
        }
    }

    public boolean elementIsPresent(String elementName) {
        return findAll(elementName).size() > 0;
    }

    public boolean elementIsPresent(String rootElement, String elementName) {
        if (findAll(rootElement).size() == 0)
            return false;
        return findAll(find(rootElement), elementName).size() != 0;
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public boolean isElementHidden(String elementName) {
        return find(elementName).getLocation().getX() == 0;
    }


    public Select selectElement(String selectName) {
        Select selectElement = new Select(find(selectName));
        return selectElement;
    }

    public void waitForJQueryLoad() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
//        ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
        boolean jqueryReady = (Boolean) javascriptExecutor.executeScript("return jQuery.active==0");
        if (!jqueryReady) {
            wait.until(jQueryLoad);
        }
    }

    public void waitUntilJSReady() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
//        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").toString().equals("complete");
        boolean jsReady = (Boolean) javascriptExecutor.executeScript("return document.readyState").toString().equals("complete");
        if (!jsReady) {
            wait.until(jsLoad);
        }
    }

    //Wait Until JQuery and JS Ready
    public void waitUntilPageReady() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        Boolean jQueryDefined = (Boolean) javascriptExecutor.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined == true) {
            sleep(0.5);
            waitForJQueryLoad();
            waitUntilJSReady();
            sleep(0.5);
        }
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }

    public void findAndUploadFile(String relativePath, String elementName, String fileName) {
        String filePath = System.getProperty("user.dir") + "/" + relativePath;
        find(elementName).sendKeys(filePath + fileName);
    }

    public static void Capture_Screenshot(String name) throws IOException {
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(scrFile, new File(ConfigLoader.getPropertiesById(SystemConstant.SCREENSHOT_FILE_LOCATION) + name + ".jpg"));
    }

    public void deleteCookies() {
        webDriver.manage().deleteAllCookies();
    }

    public String getRandomString(int numberOfCharacter) {
        String combination = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstwxyz1234567890";
        StringBuilder string = new StringBuilder();
        Random rnd = new Random();
        while (string.length() < numberOfCharacter) { // length of the random string.
            int index = (int) (rnd.nextFloat() * combination.length());
            string.append(combination.charAt(index));
        }
        String result = string.toString();
        return result;
    }

    public void waitForElement(String elementName) {
        sleep(1);
        while (!elementIsPresent(elementName)) {
            sleep(1);
        }
    }

    public List<String> getOptionsFormHiddenSelect(String elementName) {
        //String t = "//*[@name=\"contentId\"]"; sample data
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        String script = "var sb = [];"
        + "var select = document.evaluate('" + elementName + "',document,null,9,null).singleNodeValue;"
        + "for (var i=0; i<select.length; i++){" + "sb.push(select.options[i].text);" + "}" + "return sb";

        return (List<String>) js.executeScript(script);
    }

    public Map<String, String> getOptionsFormHiddenSelectWithMap(String elementName) {
        //String t = "//*[@name=\"contentId\"]"; sample data
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        String script = "var sb = {};"
        + "var select = document.evaluate('" + elementName + "',document,null,9,null).singleNodeValue;"
        + "for (var i=0; i<select.length; i++){" + "if(select.options[i].value) {sb[select.options[i].value]=select.options[i].text;}" + "}" + "return sb";

        return (Map<String, String>) js.executeScript(script);
    }

    public void waitForPageLoad() throws InterruptedException {
        Thread.sleep(2000);
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 300);
//            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }





}
