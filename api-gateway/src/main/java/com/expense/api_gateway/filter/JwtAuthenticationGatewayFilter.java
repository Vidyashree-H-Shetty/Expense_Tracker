package com.expense.api_gateway.filter;
import com.expense.api_gateway.security.JwtService;
import com.expense.api_gateway.security.RouteValidator;
import com.expense.api_gateway.util.JsonResponseUtil;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGatewayFilter implements GlobalFilter, Ordered {

    private final RouteValidator routeValidator;
    private final JwtService jwtService;
    private final JsonResponseUtil jsonResponseUtil;

    public JwtAuthenticationGatewayFilter(RouteValidator routeValidator,
                                          JwtService jwtService,
                                          JsonResponseUtil jsonResponseUtil) {
        this.routeValidator = routeValidator;
        this.jwtService = jwtService;
        this.jsonResponseUtil = jsonResponseUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Let CORS preflight pass without JWT check
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        if (!routeValidator.isSecured.test(request)) {
            return chain.filter(exchange);
        }

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return jsonResponseUtil.writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Authorization header is missing"
            );
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return jsonResponseUtil.writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Invalid Authorization header"
            );
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            return jsonResponseUtil.writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Invalid or expired JWT token"
            );
        }

        String username = jwtService.extractUsername(token);
        String roles = String.join(",", jwtService.extractRoles(token));

        ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-Authenticated-User", username)
                .header("X-Authenticated-Roles", roles)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

