package com.github.phasd.srpc.example;

import com.github.phasd.srpc.starter.annotation.EnableSimpleRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description:
 * @author: phz
 * @create: 2020-07-29 11:14:16
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableSimpleRpc(basePackageClasses = {ExampleApplication.class})
public class ExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}
}
