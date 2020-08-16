package co.com.alejoloopez5.entrega.controller;

import co.com.alejoloopez5.entrega.document.Estudiante;
import co.com.alejoloopez5.entrega.pagination.PageSupport;
import co.com.alejoloopez5.entrega.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("estudiantes")
public class EstudianteController {

    @Autowired
    private IEstudianteService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Estudiante>>> listar () {
        Flux<Estudiante> fxEstudiante = service.listar();
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxEstudiante));
    }

    @GetMapping("/descendente")
    public Mono<ResponseEntity<Flux<Estudiante>>> listarDescendente() {
        Flux<Estudiante> fxEstudiante = service.ordenarDescendente();
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxEstudiante));
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> listarPorId(@PathVariable("id") String id) {
        return service.listarPorId(id)
                .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(p)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Estudiante>> registrar(@Valid @RequestBody Estudiante estudiante, final ServerHttpRequest req) {
        return service.registrar(estudiante)
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );
    }

    @PutMapping
    public Mono<ResponseEntity<Estudiante>> modificar(@Valid @RequestBody Estudiante estudiante) {
        return service.modificar(estudiante)
                .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(p));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id) {
        return service.listarPorId(id)
                .flatMap(p -> {
                    return service.eliminar(p.getId())
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                })
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pageable")
    public Mono<ResponseEntity<PageSupport<Estudiante>>> listarPagebale(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Pageable pageRequest = PageRequest.of(page, size);
        return service.listarPage(pageRequest)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

}
