package com.bartoszj.forumwithmysql;

import com.bartoszj.forumwithmysql.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ForumWithMysqlApplication {

	public static void main(String[] args) {

		SpringApplication.run(ForumWithMysqlApplication.class, args);
	}

}
