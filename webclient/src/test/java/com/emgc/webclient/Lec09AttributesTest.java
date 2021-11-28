package com.emgc.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.emgc.webclient.dto.MultiplyRequestDto;
import com.emgc.webclient.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec09AttributesTest extends BaseTest {

	@Autowired
	private WebClient webClient;

	@Test
	public void headersTest() {
		Mono<Response> responseMono = webClient
			.post()
			.uri("reactive-math/multiply")
			.bodyValue(buildRequestDto(5, 2))
			//속성 설정에 따라 webclient의 filter메소드에 의해 알맞은 인증방식이 선택됨
			// .attribute("auth", "basic")
			.attribute("auth", "oauth")
			.retrieve()
			.bodyToMono(Response.class)
			.doOnNext(System.out::println);

		StepVerifier.create(responseMono)
			.expectNextCount(1)
			.verifyComplete();
	}

	private MultiplyRequestDto buildRequestDto(int a, int b) {
		MultiplyRequestDto dto = new MultiplyRequestDto();
		dto.setFirst(a);
		dto.setSecond(b);
		return dto;
	}

}
