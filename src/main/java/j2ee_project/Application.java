package j2ee_project;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The type Application.
 */
@EnableJpaRepositories(basePackages = "j2ee_project.repository")
@SpringBootApplication
@ServletComponentScan
public class Application extends SpringBootServletInitializer implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Application.applicationContext = applicationContext;
	}

	/**
	 * Gets context.
	 *
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return applicationContext;
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		try {
			openHomePage();
		} catch(Exception ignore) {}
	}

	private static void openHomePage() throws IOException, URISyntaxException {
		System.setProperty("java.awt.headless", "false");
		Desktop.getDesktop().browse(new URI("http://localhost:8080"));
	}

	/**
	 * Command line runner command line runner.
	 *
	 * @param ctx the ctx
	 * @return the command line runner
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
		};
	}
}
