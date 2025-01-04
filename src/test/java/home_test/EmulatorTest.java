package home_test;

import org.example.framework.BaseTest;
import org.example.utils.KeyEventUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(org.example.utils.TestListener.class)
public class EmulatorTest extends BaseTest {
    @Test
    public void moveDownTest() throws Exception {
        KeyEventUtils keyEventUtils = new KeyEventUtils(driver);
    }
}
