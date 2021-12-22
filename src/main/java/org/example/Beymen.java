package org.example;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import static org.junit.Assert.assertTrue;
import java.lang.Thread;
import java.util.List;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class Beymen {
    WebDriver webDriver;
    WebDriverWait webDriverWait;

    public Beymen(WebDriver webDriver){

        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver,30,150);
    }
    public void test() throws InterruptedException {

        //Siteye Giriş
        webDriver.get("https://www.beymen.com/");

        //Anasayfa Kontrolü
        Assert.assertEquals("Beymen.com \u2013 Lifestyle Destination", webDriver.getTitle());

        //Hesabıma gidiş
        webDriver.findElement(By.xpath("//a[@href='/customer/order']")).click();

        //Favorilerime gidiş
        webDriver.findElement(By.xpath("//a[@href='/favorilerim']")).click();

        //Sepetime gidiş
        webDriver.findElement(By.xpath("//a[@href='/cart']")).click();

        //Bu sayfada arama çubuğu olmadığı için anasayfaya dönüş
        webDriver.findElement(By.xpath("//a[@href='/']")).click();

        //"pantolan" kelimesinin aranması
        WebElement searchData = webDriver.findElement(By.cssSelector("input[placeholder='Ürün, Marka Arayın'"));
        searchData.sendKeys("pantolan");
        searchData.sendKeys(Keys.ENTER);

        //Sayfanın aşağı kaydırılması
        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,4000)");

        //Daha fazla göster butonuna basılması
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("moreContentButton"))).click();
        Thread.sleep(2000);

        //Bir ürün seçilmesi
        List<WebElement> allProducts = webDriver.findElements(By.xpath("//*[@id='productList']"));
        Random rand = new Random();
        int randomProduct = rand.nextInt(allProducts.size());
        allProducts.get(randomProduct).click();

        //Ürünün sepete eklenmesi
        webDriver.findElement(By.xpath("//*[@id='sizes']/div/span[1]")).click();
        webDriver.findElement(By.id("addBasket")).click();
        Thread.sleep(1000);

        //Ürünün fiyatının okunması
        WebElement fiyat = webDriver.findElement(By.xpath("//*[@id='priceNew']"));
        String text = fiyat.getText();
        System.out.println("ürün fiyatı: " + text);

        //Sepete gidip fiyatın okunması
        webDriver.findElement(By.xpath("//a[@href='/cart']")).click();
        String text1 = null;
        if (webDriver.findElements(By.className("m-productPrice__discount")).size() != 0) {
            WebElement sepetfiyat = webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[1]/div[2]/ul/li[4]/span[2]"));
            text1 = sepetfiyat.getText();
            System.out.println("sepet fiyatı: " + text1);
        }
        else{
            WebElement sepetfiyat = webDriver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[1]/div[2]/ul/li[3]/span[2]"));
            text1 = sepetfiyat.getText();
            System.out.println("sepet fiyatı: " + text1);
        }

        //Ürün ve Sepet Fiyatının kıyaslanması
        if (text.equals(text1)) {
            System.out.println("Ürün ve sepet fiyatı birbiriyle aynı");
        } else {
            System.out.println("Ürün ve sepet fiyatı birbirinden farklı!");
        }

        //Ürün adedinin 2 olarak seçilmesi ve seçildiğinin bildirimle doğrulanması
        Select urunAdedi = new Select(webDriver.findElement(By.id("quantitySelect0")));
        urunAdedi.selectByIndex(1);
        WebElement adetKontrol = webDriverWait.until((ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='notifyMessage']"))));
        Assert.assertTrue(adetKontrol.getText().contains("Ürün adetiniz güncellenmiştir."));
        Thread.sleep(1000);

        //Sepetin boşaltılması ve bildirimin doğrulanması
        webDriver.findElement(By.id("removeCartItemBtn0")).click();
        Thread.sleep(1000);
        WebElement sepetKontrol = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='notifyTitle']")));
        Assert.assertTrue(sepetKontrol.getText().contains("Ürün Silindi"));

        Thread.sleep(1000);
    }
}