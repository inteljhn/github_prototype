package com.liqu.wiki.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WikiControllerTest {

	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello 도현!</h1>";
	}
}
