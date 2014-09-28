package hello;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan
@EnableAsync
public class Application  {

	private Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private FacebookLookupService facebookLookupService;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
		Application app = context.getBean(Application.class);
		app.printCompanyInfo();		
		app.printCompanyInfoSync();
		context.close();
	}
	
	public void printCompanyInfo() throws InterruptedException, ExecutionException {		
		
		long start = System.currentTimeMillis();
		Future<Page> page1 = facebookLookupService.lookupFacebook("cognizant");
		Future<Page> page2 = facebookLookupService.lookupFacebook("infosys");		
		Future<Page> page3 = facebookLookupService.lookupFacebook("ndtv");		
		Future<Page> page4 = facebookLookupService.lookupFacebook("google");		
		Future<Page> page5 = facebookLookupService.lookupFacebook("ibm");

		while (! (page1.isDone() && page2.isDone() && page3.isDone() && page4.isDone() && page5.isDone())) {
			Thread.sleep(10);
		}
				
		logger.info(page1.get().toString());
		logger.info(page2.get().toString());
		logger.info(page3.get().toString());
		logger.info(page4.get().toString());
		logger.info(page5.get().toString());
		long end = System.currentTimeMillis();		
		logger.info( "Time elapsed = " + (end-start) + " milli seconds.");	
	}
	
	public void printCompanyInfoSync() throws InterruptedException {		
		long start = System.currentTimeMillis();
		Page page1 = facebookLookupService.lookupFacebookSync("cognizant");
		Page page2 = facebookLookupService.lookupFacebookSync("infosys");		
		Page page3 = facebookLookupService.lookupFacebookSync("ndtv");		
		Page page4 = facebookLookupService.lookupFacebookSync("google");		
		Page page5 = facebookLookupService.lookupFacebookSync("ibm");
		
		logger.info(page1.toString());
		logger.info(page2.toString());
		logger.info(page3.toString());
		logger.info(page4.toString());
		logger.info(page5.toString());

		long end = System.currentTimeMillis();		
		logger.info( "Time elapsed = " + (end-start) + " milli seconds.");	
	}	

}
