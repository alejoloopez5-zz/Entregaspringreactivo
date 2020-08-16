package co.com.alejoloopez5.entrega.service.impl;

import co.com.alejoloopez5.entrega.document.Estudiante;
import co.com.alejoloopez5.entrega.repo.IEstudianteRepo;
import co.com.alejoloopez5.entrega.repo.IGenericRepo;
import co.com.alejoloopez5.entrega.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EstudianteServiceImpl extends CrudImpl<Estudiante,String> implements IEstudianteService {

    @Autowired
    private IEstudianteRepo repo;

    @Override
    protected IGenericRepo<Estudiante, String> getRepo() {
        return repo;
    }

    @Override
    public Flux<Estudiante> ordenarDescendente() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "nombres"));
    }

}
