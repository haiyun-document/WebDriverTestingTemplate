package webdriver.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import qa.webdriver.util.WebDriverUtils;

/**
 *  This class tests a multi-window handling method that I call "handle cache"
 *  
 * @author Jon Thor Austen
 *
 */
public class TestHandleCacheOne extends WebDriverUtils {
	
	/**
	 *  Setup web server before loading class
	 */
	@BeforeClass
	public static void setUpTestHandleCacheOneClass() {
		logger.info("Finished TestHandleCacheOneClass");
	}
	
	/**
	 *  Load main window handle before tests
	 */
	public TestHandleCacheOne() {
		logger.info("Constructed TestHandleCacheOne");
	}
	
	/**
	 *  Start main window handle for tests
	 */
	@Before
	public void setUpTestHandleCacheOne() {
		xOffSet = 0;
		initializeRemoteBrowser( "firefox", "localhost", 4444 );
		System.out.println("HandleCacheOne thread id = " + Thread.currentThread().getId());
		logger.info("Finished setUpTestHandleCacheOne");
	}
	
	/**
	 *  Tests opening a few windows and then closing them
	 */
	@Test
	public void testHandleCacheOne() {
		logger.info("Starting test testHandleCacheOne" );
		logger.info("Loading Window1 contents");
		driver.get("http://localhost:8080/httproot/index.html");
		waitTimer(2, 500);

		// Open Window2 via Window1
		logger.info("Opening Window2");
		driver.findElement( By.id("btnNewNamelessWindow") ).click();        
		String h2 = handleNewWindow();
		waitTimer(2, 500);

		// Open Window3 and Window4 via Window2
		//driver.findElement( By.cssSelector("html body a:first-child") ).click();
		logger.info("Opening Window3");
		driver.findElement(By.id("w3link")).click();
		String h3 = handleNewWindow();
		waitTimer(2, 500);
		driver.switchTo().window( h2 );
		//driver.findElement( By.cssSelector("html body a:last-child") ).click();
		logger.info("Opening Window4");
		driver.findElement(By.id("w4link")).click();
		waitTimer(2, 500);
		String h4 = handleNewWindow();
		waitTimer(2, 500);

		// close Window4
		closeWindowByHandle( h4 );
		updateHandleCache();
		waitTimer(6, 500);

		// close Window3
		closeWindowByHandle( h3 );
		updateHandleCache();
		waitTimer(2, 500);

		// close Window2
		closeWindowByHandle( h2 );
		updateHandleCache();        
		waitTimer(2, 500);         

		logger.info( "Finished testHandleCacheOne" );
	}	

	/**
	 *  Close main window handle after tests finish
	 */
	@After
	public void tearDownTestHandleCacheOne() {
		// close main window handle
		driver.switchTo().window( mainHandle );
		driver.get("about:about");
		updateHandleCache();  
		waitTimer(6, 500);
		logger.info("Finished tearDownTestHandleCacheOne");
	}

	/**
	 *  Cleanup any remaining objects after unloading class
	 */
	@AfterClass
	public static void tearDownTestHandleCacheOneClass() {
		closeAllBrowserWindows(); 
		logger.info("Finished tearDownTestHandleCacheOneClass");
	}

}
