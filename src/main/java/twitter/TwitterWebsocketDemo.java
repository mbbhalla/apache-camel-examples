package twitter;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.TwitterComponent;
import org.apache.camel.component.websocket.WebsocketComponent;
import org.apache.camel.impl.DefaultCamelContext;

public final class TwitterWebsocketDemo extends RouteBuilder {
	
	private final String searchTerm;
	
    private final String consumerKey = "mAdmAPcmcQoDrWVYFdmOD4UY9";
    private final String consumerSecret = "somjH1B11YymLXSib6IwwZcvSMGEKdoRtGglb0UqIR9fQy6UgC";
    private final String accessToken = "12291092-z7G2mPpBPRhnHVFPsIQu5vGgdTt8wslfO2rmAL1tC";
    private final String accessTokenSecret = "cAa6XJzwAlZSPuDrmoOMh75OraPhyvbWOcsA6NFUGDCkM";
    
	public TwitterWebsocketDemo(final String searchTerm) {
		this.searchTerm = searchTerm;
		
		//configure Twitter component of this context
		TwitterComponent tc = getContext().getComponent("twitter", TwitterComponent.class);
		tc.setAccessToken(accessToken);
	    tc.setAccessTokenSecret(accessTokenSecret);
	    tc.setConsumerKey(consumerKey);
	    tc.setConsumerSecret(consumerSecret);
	    
	    
	    WebsocketComponent wc = getContext().getComponent("websocket", WebsocketComponent.class);
        wc.setStaticResources("classpath:.");

	}

	@Override
	public void configure() throws Exception {
		// poll twitter search for new tweets
        fromF("twitter://search?type=polling&delay=%s&keywords=%s", 1000, searchTerm)
        	.to("log:tweet?level=INFO")
            // push tweets to all web socket subscribers on camel-tweet
            .to("websocket:camel-tweet?sendToAll=true");
		
	}
	
	public static void main(String[] args) throws Exception {
		TwitterWebsocketDemo tws = new TwitterWebsocketDemo("happy");
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(tws);
		context.start();
		Thread.sleep(600000);
		context.stop();
	}
}



