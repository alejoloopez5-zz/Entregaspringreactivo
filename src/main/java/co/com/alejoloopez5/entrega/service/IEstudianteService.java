package co.com.alejoloopez5.entrega.service;

import co.com.alejoloopez5.entrega.document.Estudiante;
import reactor.core.publisher.Flux;

public interface IEstudianteService extends ICrud<Estudiante,String>{

    Flux<Estudiante> ordenarDescendente();
}
