package org.example;
import org.junit.Test;
import org.openqa.selenium.devtools.v85.page.Page;
import sun.rmi.runtime.Log;
import org.apache.log4j.Logger;

public class LoginPageTest extends PageTest {

    @Test
    public void anasayfa() throws InterruptedException {
        new Beymen(driver).test();
    }
}
