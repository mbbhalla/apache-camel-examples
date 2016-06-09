package copier;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileCopier extends RouteBuilder {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:input/orders?noop=true")
					.choice()
						.when(xpath("/order/type = 'freereplacement'"))
							.to("file:output/orders/freerep")
						.otherwise()
							.to("file:output/orders/normal")
					.end();
			}
		});
		
		context.start();
		Thread.sleep(200000);
		context.stop();
	}

	@Override
	public void configure() throws Exception {
		from("file:input/orders?noop=true")
		.choice()
			.when(xpath("/order/type = 'freereplacement'"))
				.to("file:output/orders/freerep")
			.otherwise()
				.to("file:output/orders/normal")
		.end();
		
	}
}
