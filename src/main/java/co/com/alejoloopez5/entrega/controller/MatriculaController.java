package co.com.alejoloopez5.entrega.controller;

import co.com.alejoloopez5.entrega.document.Matricula;
import co.com.alejoloopez5.entrega.service.ICursoService;
import co.com.alejoloopez5.entrega.service.IEstudianteService;
import co.com.alejoloopez5.entrega.service.IMatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("matriculas")
public class MatriculaController {

    @Autowired
    private IMatriculaService service;

    @Autowired
    private IEstudianteService estudianteService;

    @Autowired
    private ICursoService cursoService;

    @PostMapping
    public Mono<ResponseEntity<Matricula>> registrar(@Valid @RequestBody Matricula matricula, final ServerHttpRequest req) {
        return service.registrar(matricula)
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );
    }

    @GetMapping()
    public Mono<ResponseEntity<Flux<Matricula>>> listar(){
         Flux<Matricula> fxMatriculas = service.listar()
                .flatMap(f -> {
                    return Mono.just(f)
                            .zipWith(estudianteService.listarPorId(f.getEstudiante().getId()), (ma, es) -> {
                                ma.setEstudiante(es);
                                return ma;
                            });
                })
                 .flatMap(f -> {
                     return Flux.fromIterable(f.getCursos()).flatMap(cu -> {
                         return cursoService.listarPorId(cu.getId());
                     }).collectList().flatMap(list -> {
                         f.setCursos(list);
                         return Mono.just(f);
                     });
                 });

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxMatriculas)
        );
    }

    @GetMapping("/listar{id}")
    public Mono<ResponseEntity<Matricula>> listarPorId(@PathVariable("id") String id){
        return service.listarPorId(id)
                .flatMap(f -> {
                    return Mono.just(f)
                            .zipWith(estudianteService.listarPorId(f.getEstudiante().getId()), (ma, es) -> {
                                ma.setEstudiante(es);
                                return ma;
                            });
                })
                .flatMap(f -> {
                    return Flux.fromIterable(f.getCursos()).flatMap(cu -> {
                        return cursoService.listarPorId(cu.getId());
                    }).collectList().flatMap(list -> {
                        f.setCursos(list);
                        return Mono.just(f);
                    });
                })
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<ResponseEntity<Matricula>> modificar(@Valid @RequestBody Matricula matricula) {
        return service.modificar(matricula)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)
                );
    }

    @DeleteMapping("/{id}")
    //@RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE})
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id){
        return service.listarPorId(id)
                .flatMap(p -> {
                    return service.eliminar(p.getId())
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                })
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

    }
}
