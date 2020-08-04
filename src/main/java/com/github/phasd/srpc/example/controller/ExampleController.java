package com.github.phasd.srpc.example.controller;

import com.alibaba.fastjson.JSON;
import com.github.phasd.srpc.core.rpc.SimpleRpc;
import com.github.phasd.srpc.core.rpc.request.Request;
import com.github.phasd.srpc.example.rpc.TestRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: phz
 * @create: 2020-07-29 11:19:56
 */
@RestController
@RequestMapping("/example")
public class ExampleController {

	@Autowired
	private SimpleRpc simpleRpc;

	@Autowired
	private TestRpc testRpc;


	@PostMapping("/test1")
	public ResponseEntity<Object> test1() {
		Request<Object> build = Request.post("test/example/pathTest/{id}/{name}").uriParam("id", "10101").uriParam("name", "zs").build();
		String res = simpleRpc.getForObject(build, String.class);
		System.out.println(res);

		testRpc.bodyTest(Collections.singletonMap("name", "zs")).thenAccept(System.out::println);

		List<List<Map<String, String>>> lists = testRpc.bodytest2(Collections.singletonMap("name", "zs"));
		System.out.println(JSON.toJSONString(lists));
		Map<String, String> map = testRpc.bodytest3(Collections.singletonMap("name", "zs"));
		System.out.println(JSON.toJSONString(map));
		Map<String, List<Map<String, String>>> stringListMap = testRpc.bodytest4(Collections.singletonMap("name", "zs"));
		System.out.println(JSON.toJSONString(stringListMap));

		testRpc.bodytest5(Collections.singletonMap("name", "zs"));

		testRpc.bodytest6(Collections.singletonMap("name", "zs")).thenAcceptAsync(t -> {
			System.out.println("异步调用");
			System.out.println(t);
		});
		return ResponseEntity.ok("ok");
	}

	@PostMapping("/test2")
	public ResponseEntity<Object> test2() {
		ByteArrayResource byteArrayResource = new ByteArrayResource("jljkljlkjlk".getBytes()) {
			@Override
			public String getFilename() {
				return "测试txt";
			}
		};
		Request<Object> build = Request.post("test/example/paramTest")
				.formParam("id", "10101")
				.formParam("name", "zs")
				.formParam("file", byteArrayResource)
				.build();

		List<String> forList = simpleRpc.getForList(build, String.class);
		return ResponseEntity.ok(forList);
	}


	@PostMapping("/pathTest/{id}/{name}")
	public ResponseEntity<Object> pathTest(@PathVariable("id") String id, @PathVariable("name") String name) {
		System.out.println("id:" + id);
		System.out.println("name:" + name);
		return ResponseEntity.ok(name);
	}

	@PostMapping("/paramTest")
	public ResponseEntity<Object> paramTest(@RequestParam("id") List<String> id, @RequestParam("name") List<String> name, @RequestPart("file") MultipartFile file) {
		System.out.println(id);
		System.out.println(name);
		System.out.println(file.getOriginalFilename());
		return ResponseEntity.ok(name);
	}

	@PostMapping("/bodyTest")
	public ResponseEntity<Object> bodyTest(@RequestBody Map<String, String> param) {
		System.out.println(param);
		return ResponseEntity.ok(Collections.singletonList("sdsdd"));
	}

	@PostMapping("bodytest2")
	public ResponseEntity<?> bodytest2(@RequestBody Map<String, String> param) {
		System.out.println(JSON.toJSONString(param));
		return ResponseEntity.ok().body(Collections.singletonList(Collections.singletonList(param)));
	}

	@PostMapping("bodytest3")
	public ResponseEntity<?> bodytest3(@RequestBody Map<String, String> param) {
		System.out.println(JSON.toJSONString(param));
		return ResponseEntity.ok().body(param);
	}

	@PostMapping("bodytest4")
	public ResponseEntity<?> bodytest4(@RequestBody Map<String, String> param) {
		System.out.println(JSON.toJSONString(param));
		return ResponseEntity.ok().body(Collections.singletonMap("test", Collections.singletonList(param)));
	}

	@PostMapping("bodytest5")
	public void bodytest5(@RequestBody Map<String, String> param) {
		System.out.println(JSON.toJSONString(param));
	}
}
