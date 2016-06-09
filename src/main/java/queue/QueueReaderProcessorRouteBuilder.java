package queue;

import org.apache.camel.builder.RouteBuilder;

public class QueueReaderProcessorRouteBuilder extends RouteBuilder {

	/*public QueueReaderProcessorRouteBuilder() {
		JndiRegistry registry = (JndiRegistry) (
				(PropertyPlaceholderDelegateRegistry)getContext().getRegistry()).getRegistry();
		registry.bind("sqsClient", arg1);
	}*/
	
	@Override
	public void configure() throws Exception {
		 from("aws-sqs://demo-camel?amazonSQSClient=#sqsClient&deleteAfterRead=true&maxMessagesPerPoll=10&concurrentConsumers=5")
		 	.choice()
		 		.when().jsonpath("$[?(@.Subject == 'CRN')]")
		 		 //combine predicates
		 		.transform()
		 		.simple("${body.concat(\" ->OK\")}")	
		 	.to("stream:out")
		 .end();
	}

}
