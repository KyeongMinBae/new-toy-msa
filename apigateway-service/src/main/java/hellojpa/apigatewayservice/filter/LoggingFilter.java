package hellojpa.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            // Custom Pre Filter
//            log.info("Logging Filter baseMessage: {}, {}", config.getBaseMessage(), request.getRemoteAddress());
//            if (config.isPreLogger()) {
//                log.info("Logging Filter: request uri -> {}", request.getURI().toString());
//            }
//
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                if (config.isPostLogger()) {
//                    // Custom Post Filter
//                    log.info("Logging Filter: response code -> {}", response.getStatusCode());
//                }
//            }));
//        };
//    }

    /* 우선 순위를 갖는 Logging Filter 적용 */
    @Override
    public GatewayFilter apply(Config config) {

        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // Custom Pre Filter
        log.info("Logging Filter baseMessage: {}, {}", config.getBaseMessage(), request.getRemoteAddress());
        if (config.isPreLogger()) {
            log.info("Logging Filter: request uri -> {}", request.getURI().toString());
        }

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            if (config.isPostLogger()) {
                // Custom Post Filter
                log.info("Logging Filter: response code -> {}", response.getStatusCode());
            }
            }));
        }, Ordered.HIGHEST_PRECEDENCE);

        return filter;
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean PreLogger;
        private boolean PostLogger;
    }

}
