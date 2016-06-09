package console;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ConsoleStringTransformer extends RouteBuilder {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("stream:in?promptMessage=Enter: ")
					.transform()
						.simple("${body.toUpperCase()}")
				.to("stream:out");
			}
		});
		
		context.start();
		Thread.sleep(1000000);
		context.stop();
	}

	@Override
	public void configure() throws Exception {
		from("stream:in?promptMessage=Enter: ")
		.transform()
			.simple("${body.toUpperCase()}")
		.to("stream:out")
		.end();
	}
}
