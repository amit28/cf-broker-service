package init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;


@PropertySource("application.properties")
@SpringBootApplication
public class ApplicationInitializer 
{
	@Bean
	public Cloud getCloud(){
		return new CloudFactory().getCloud();
	}
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationInitializer.class, args);
	}
}