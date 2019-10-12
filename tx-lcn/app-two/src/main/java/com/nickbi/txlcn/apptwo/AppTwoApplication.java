package com.nickbi.txlcn.apptwo;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDistributedTransaction
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.nickbi.txlcn.apptwo.dao")
public class AppTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppTwoApplication.class, args);
	}

}
