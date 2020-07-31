package com.senld.gzlt.flowBuy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senld.gzlt.flowBuy.base.RespBodyObj;
import com.senld.gzlt.flowBuy.config.CacheLock;
import com.senld.gzlt.flowBuy.config.CacheParam;
import com.senld.gzlt.flowBuy.service.FlowOrderService;

@RestController
@RequestMapping("/lock")
public class LockController {

	@Autowired
	private FlowOrderService flowOrderService;

	@CacheLock(prefix = "test")
	@GetMapping("/test")
	public String query(@CacheParam(name = "token") @RequestParam String token) {
		return "success - " + token;
	}

	@PostMapping("/say")
	public RespBodyObj<?> say(@CacheParam(name = "content") @RequestBody Map<String, Object> params) {
		return flowOrderService.update(params);
	}

}
