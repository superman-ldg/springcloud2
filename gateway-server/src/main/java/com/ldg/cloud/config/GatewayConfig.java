package com.ldg.cloud.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator add(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes= builder.routes();
        routes.route("myroute",r->r.path("/ldg").filters((Function<GatewayFilterSpec, UriSpec>) new MyGatewayFilter())
        .uri("http://www.baidu.com")).build();
        return routes.build();
    }

    @Bean
    public RouteLocator customerRoute(RouteLocatorBuilder builder){
        return builder.routes().route(r->r.path("/fluent/customer/**")
        .filters(f->f.stripPrefix(1)
        .filter(new GatewayFilter2())
        .addResponseHeader("X-Response-Default-Foo","Default-Bar"))
        .uri("lb://CONSUMER")
        .order(0)
        .id("myroute2")).build();
    }

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }

    //ip限流
    @Bean
    public KeyResolver userKeyResolverIp(){
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
    //用户限流
    @Bean
    public KeyResolver userKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }

    //接口限流
    @Bean
    public KeyResolver apiKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

}
