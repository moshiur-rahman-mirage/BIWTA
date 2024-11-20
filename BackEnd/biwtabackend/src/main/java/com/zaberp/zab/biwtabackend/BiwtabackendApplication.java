package com.zaberp.zab.biwtabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "com.zaberp.zab.biwtabackend")
public class BiwtabackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiwtabackendApplication.class, args);
	}

}
