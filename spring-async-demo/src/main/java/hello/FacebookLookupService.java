package hello;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacebookLookupService {

	private Logger logger = LoggerFactory.getLogger(FacebookLookupService.class);

	private RestTemplate restTemplate = new RestTemplate();
	
	@Async
	public Future<Page> lookupFacebook(String name) throws InterruptedException {
		logger.info("Looking up " + name);
		Page page = restTemplate.getForObject("http://graph.facebook.com/" + name, Page.class);		
		return new AsyncResult<Page>(page);
	}
	
	public Page lookupFacebookSync(String name) throws InterruptedException {
		logger.info("Looking up " + name);
		Page page = restTemplate.getForObject("http://graph.facebook.com/" + name, Page.class);	
		return page;
	}
}
