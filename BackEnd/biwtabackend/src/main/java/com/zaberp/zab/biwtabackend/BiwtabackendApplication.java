package com.zaberp.zab.biwtabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

//@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "com.zaberp.zab.biwtabackend")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)

@EnableJpaAuditing
public class BiwtabackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiwtabackendApplication.class, args);
	}

}
