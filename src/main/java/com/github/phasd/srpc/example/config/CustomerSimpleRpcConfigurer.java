package com.github.phasd.srpc.example.config;

import com.github.phasd.srpc.core.rpc.interceptor.RpcPostInterceptor;
import com.github.phasd.srpc.core.rpc.interceptor.RpcPreInterceptor;
import com.github.phasd.srpc.core.rpc.interceptor.SimpleRpcConfigRegister;
import com.github.phasd.srpc.core.rpc.interceptor.SimpleRpcConfigurer;
import com.github.phasd.srpc.core.rpc.request.Request;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;

/**
 * @description:
 * @author: phz
 * @create: 2020-07-31 15:23:23
 */
@Configuration
public class CustomerSimpleRpcConfigurer implements SimpleRpcConfigurer {
	@Override
	public void configure(SimpleRpcConfigRegister simpleRpcConfigRegister) {
		simpleRpcConfigRegister.addPreInterceptor(new RpcPreInterceptor() {

			@Override
			public int getOrder() {
				return 0;
			}

			@Override
			public void preInterceptor(HttpRequest request) {
				System.out.println(request.getHeaders());
			}

			@Override
			public boolean preSupports(Request request) {
				System.out.println(request.getUrl());
				return true;
			}
		});


		simpleRpcConfigRegister.addPostInterceptor(new RpcPostInterceptor() {
			@Override
			public String postInterceptor(Request request, HttpHeaders headers, String content) {
				System.out.println(request.getUrl());
				return content;
			}

			@Override
			public boolean postSupports(Request request, HttpHeaders headers) {
				return false;
			}

			@Override
			public int getOrder() {
				return 0;
			}
		});
	}
}
