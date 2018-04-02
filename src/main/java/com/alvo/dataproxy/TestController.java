package com.alvo.dataproxy;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class TestController {

  public Mono<ServerResponse> hello(ServerRequest request) {
    final Mono<String> req = request.bodyToMono(String.class);
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(req, String.class);
  }
}
