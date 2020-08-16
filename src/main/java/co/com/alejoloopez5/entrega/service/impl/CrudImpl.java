package co.com.alejoloopez5.entrega.service.impl;

import co.com.alejoloopez5.entrega.pagination.PageSupport;
import co.com.alejoloopez5.entrega.repo.IGenericRepo;
import co.com.alejoloopez5.entrega.service.ICrud;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public abstract class CrudImpl<T,ID> implements ICrud<T,ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public Mono<T> registrar(T obj) {
        return getRepo().save(obj);
    }

    @Override
    public Mono<T> modificar(T obj) {
        return getRepo().save(obj);
    }

    @Override
    public Flux<T> listar() {
        return getRepo().findAll();
    }

    @Override
    public Mono<T> listarPorId(ID id) {
        return getRepo().findById(id);
    }

    @Override
    public Mono<Void> eliminar(ID id) {
        return getRepo().deleteById(id);
    }

    @Override
    public Mono<PageSupport<T>> listarPage(Pageable page) {
        return getRepo().findAll()
                .collectList()
                .map(list -> new PageSupport<>(
                        list
                                .stream()
                                .skip(page.getPageNumber() * page.getPageSize())
                                .limit(page.getPageSize())
                                .collect(Collectors.toList()),
                        page.getPageNumber(), page.getPageSize(), list.size()
                ));
    }
}
