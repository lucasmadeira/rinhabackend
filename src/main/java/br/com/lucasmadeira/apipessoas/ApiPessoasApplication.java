package br.com.lucasmadeira.apipessoas;

import org.eclipse.jetty.util.thread.ThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiPessoasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPessoasApplication.class, args);
	}



}
