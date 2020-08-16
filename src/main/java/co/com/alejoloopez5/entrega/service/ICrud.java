package co.com.alejoloopez5.entrega.service;

import co.com.alejoloopez5.entrega.pagination.PageSupport;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICrud<T,ID> {

    Mono<T>     registrar(T obj);
    Mono<T>     modificar(T obj);
    Flux<T>     listar();
    Mono<T>     listarPorId(ID id);
    Mono<Void>  eliminar(ID id);
    Mono<PageSupport<T>> listarPage(Pageable page);
}
