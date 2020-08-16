package co.com.alejoloopez5.entrega;

import co.com.alejoloopez5.entrega.handler.CursoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> rutasCurso(CursoHandler handler) {
        return route(GET("/cursos"), handler::listar)
                .andRoute(GET("/cursos/{id}"), handler::listarPorId)
                .andRoute(POST("/cursos"), handler::registrar)
                .andRoute(PUT("/cursos"), handler::modificar)
                .andRoute(DELETE("/cursos/{id}"), handler::eliminar);
    }

}
