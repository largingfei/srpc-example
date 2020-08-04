package com.github.phasd.srpc.example.rpc;

import com.github.phasd.srpc.starter.annotation.Rpc;
import com.github.phasd.srpc.starter.annotation.RpcClient;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author: phz
 * @create: 2020-07-28 14:00:34
 */
@RpcClient(baseUrl = "test")
public interface TestRpc {
	@Rpc(url = "/example/pathTest/{id}/{name}", method = HttpMethod.POST)
	String pathTest(@PathVariable("id") String id, @PathVariable("name") String name);

	@Rpc(url = "/example/paramTest", method = HttpMethod.POST)
	String paramTest(@RequestParam("id") List<String> id, @RequestParam("name") List<String> name, @RequestPart("file") Resource file);

	@Rpc(url = "/example/bodyTest", method = HttpMethod.POST, async = true)
	CompletableFuture<List<String>> bodyTest(@RequestBody Map<String, String> param);

	@Rpc(url = "example/bodytest2", method = HttpMethod.POST)
	List<List<Map<String, String>>> bodytest2(@RequestBody Map<String, String> param);

	@Rpc(url = "example/bodytest3", method = HttpMethod.POST)
	Map<String, String> bodytest3(@RequestBody Map<String, String> param);

	@Rpc(url = "example/bodytest4", method = HttpMethod.POST)
	Map<String, List<Map<String, String>>> bodytest4(@RequestBody Map<String, String> param);

	@Rpc(url = "example/bodytest5", method = HttpMethod.POST)
	void bodytest5(@RequestBody Map<String, String> param);

	@Rpc(url = "example/bodytest3", method = HttpMethod.POST, async = true)
	CompletableFuture<Map<String, String>> bodytest6(@RequestBody Map<String, String> param);
}
