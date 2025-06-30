package Java1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TechShopTests {

    WebDriver driver;
    WebDriverWait wait;

    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\kjpre\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.get("https://techshopbd.com");
    }
    
    public void signIn() {
    	WebElement signInLink = driver.findElement(By.cssSelector("a.sign-in-design"));
    	signInLink.click();
    	// Wait for the email input field to be visible and enter the email
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	    By.name("emailOrPhone")));
    	emailInput.sendKeys("viya0298@gmail.com");  // Replace with desired test email

    	// Click the "Next" button
    	WebElement nextButton = driver.findElement(By.xpath("//button[contains(text(), 'Next')]"));
    	nextButton.click();
    	
    	// Wait until the password input is visible
    	//WebDriverWait wait = new WebDriverWait(driver, 10);
    	WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	    By.name("password")));
    	passwordInput.sendKeys("@Priya_123#");  // Replace with actual test password

    	// Click the "Login" button
    	WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Login')]"));
    	loginButton.click();
    }

    public void openSiteAndSearch() {
        try {
        	Thread.sleep(5000);
            WebElement searchBox = driver.findElement(By.id("searchInput"));
            searchBox.sendKeys("ard");
            Thread.sleep(5000);

            WebElement searchButton = driver.findElement(By.id("searchButton"));
            searchButton.click();

            wait.until(ExpectedConditions.urlContains("search"));
            System.out.println("Pass - Search completed successfully.");
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Fail - Failed at search step: " + e.getMessage());
        }
    }
    
    public void applyPriceLowToHighFilter() {
        try {
            
            WebElement lowToHighRadio = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("price_sort_lowToHigh")));
            lowToHighRadio.click();
            Thread.sleep(5000);
            System.out.println("Pass - Price Low to High filter clicked.");

            
            // Optionally: Validate that prices are in ascending order
            List<WebElement> priceElements = driver.findElements(By.cssSelector(".card-body .text-muted"));
            List<Double> prices = new ArrayList<>();

            for (WebElement priceElement : priceElements) {
                String priceText = priceElement.getText().replace("৳", "").replace(",", "").trim();
                try {
                    prices.add(Double.parseDouble(priceText));
                } catch (NumberFormatException e) {
                    System.out.println("---- Skipped non-numeric price: " + priceText);
                }
            }

            if (isSortedAscending(prices)) {
                System.out.println("Pass - Products are sorted from Low to High.");
            } else {
                System.out.println("Fail - Products are NOT sorted correctly.");
            }

        } catch (Exception e) {
            System.out.println("Fail - Failed to apply filter: " + e.getMessage());
        }
    }

    // Helper method to check ascending sort
    public boolean isSortedAscending(List<Double> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }


    public void clickProduct() {
        try {
        	driver.get("https://techshopbd.com/product/acrylic-uno-base");       
            

            if (driver.getCurrentUrl().contains("/product/acrylic-uno-base")) {
                System.out.println("Pass - Navigated to product page.");
            } else {
                System.out.println("Fail - Product page navigation failed.");
            }
        } catch (Exception e) {
            System.out.println("Fail - Error clicking product: " + e.getMessage());
        }
    }

    public void clickAddToCart() {
        try {
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Add to Cart')]")));
            addToCartBtn.click();
            Thread.sleep(5000);
            System.out.println("Pass - Add to Cart button clicked.");
        } catch (Exception e) {
            System.out.println("Fail - Failed to click Add to Cart: " + e.getMessage());
        }
    }

    public void viewCart() {
        try {
        	WebElement viewCartBtn = driver.findElement(By.linkText("VIEW CART"));
        	viewCartBtn.click();
        	
        	WebElement checkoutBtn = driver.findElement(By.linkText("Checkout →"));
        	checkoutBtn.click();

        } catch (Exception e) {
            System.out.println("Fail - Error while view cart " + e.getMessage());
        }
    }
    
    public void fillDetailsAndOrder() {
    	WebDriverWait wait = new WebDriverWait(driver, 10);

    	WebElement addressDropdown = wait.until(ExpectedConditions.elementToBeClickable(
    	        By.name("address_label")));
    	Select labelSelect = new Select(addressDropdown);
    	labelSelect.selectByVisibleText("Home");

    	// 2️⃣ Fill Full Name
    	driver.findElement(By.name("full_name")).sendKeys("Priya K");

    	// 3️⃣ Fill Phone
    	driver.findElement(By.name("phone")).sendKeys("8809008506730");

    	// 4️⃣ Fill Email
    	driver.findElement(By.name("email")).sendKeys("viyao298@gmail.com");

    	// 5️⃣ Fill District
    	driver.findElement(By.name("district")).sendKeys("D.K.");

    	// 6️⃣ Fill Thana
    	driver.findElement(By.name("thana")).sendKeys("Nandini layout");

    	// 7️⃣ Fill Postal Code
    	driver.findElement(By.name("postal_code")).sendKeys("575 004");

    	// 8️⃣ Fill Full Address
    	driver.findElement(By.name("address")).sendKeys("#98-231, kodial bail, City hill apartments, Gear station road, City circle - 575 004");

    	// 9️⃣ Click "Confirm Order" button
    	WebElement confirmOrderBtn = wait.until(ExpectedConditions.elementToBeClickable(
    	        By.xpath("//button[contains(text(), 'Confirm Order')]")));
    	confirmOrderBtn.click();

    	System.out.println("✅ Address filled and Confirm Order button clicked.");

    }
    
 // ✅ NEW: Sign Up Test Logic
    public void signUp() {
        try {
            driver.get("https://techshopbd.com/sign-in");
            Thread.sleep(5000);
         // Wait for the email input box
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[name='emailOrPhone']")));
            emailInput.sendKeys("viya0298@gmail.com"); // Use valid email
            Thread.sleep(5000);
            // Click the "Next" button
            WebElement nextButton = driver.findElement(By.xpath("//button[text()='Next']"));
            nextButton.click();
            Thread.sleep(5000);
            System.out.println("Pass - Email entered and Next button clicked.");
            
            WebElement fullNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[name='full_name']")));
            fullNameInput.sendKeys("Priya K"); 
            
            WebElement phone = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[name='phone']")));
            phone.sendKeys("8809008506730");

            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[name='registration_password']")));
            password.sendKeys("@Priya123#");

            System.out.println("Pass - Sign-up form filled.");

            WebElement sendOtpBtn = driver.findElement(By.id("sendOtpButton"));
            sendOtpBtn.click();
            Thread.sleep(5000);
            System.out.println("Pass - Send OTP button clicked. (You may handle OTP next.)");

        } catch (Exception e) {
            System.out.println("Fail - Sign-up process failed: " + e.getMessage());
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("**** Browser closed.****");
        }
    }

    public static void main(String[] args) {
    	TechShopTests test = new TechShopTests();

        test.setup();
        
        test.signIn();
        
        test.openSiteAndSearch();
        test.applyPriceLowToHighFilter();
        test.clickProduct();
        test.clickAddToCart();
        test.viewCart();
        test.fillDetailsAndOrder();
        
        //test.signUp();
        
        //test.tearDown();
    }
}
