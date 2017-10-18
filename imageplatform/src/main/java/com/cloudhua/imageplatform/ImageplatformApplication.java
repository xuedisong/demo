/**
 *注册bean:@Component,@Controller,@Service,@Repository(库)=persistence(持久层)，放到IoC容器中。
 *注册：@Bean 用在方法上，告诉Spring容器，你可以从下面这个方法中拿到一对象，该对象会被注册成bean，前提是将该方法所在类拿@Configuration注解
 *使用bean：byTYPE（@Autowired）、byNAME（@Resource）
 *
 */
package com.cloudhua.imageplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication//@ComponentScan
@EnableCaching
public class ImageplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageplatformApplication.class, args);//利用ImageplatformApplication类的反射，将该类加载。并扫描所在目录的所有bean
	}
}
