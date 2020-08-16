package co.com.alejoloopez5.entrega.service.impl;

import co.com.alejoloopez5.entrega.document.Curso;
import co.com.alejoloopez5.entrega.repo.ICursoRepo;
import co.com.alejoloopez5.entrega.repo.IGenericRepo;
import co.com.alejoloopez5.entrega.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoServiceImpl extends CrudImpl<Curso, String> implements ICursoService {

    @Autowired
    private ICursoRepo repo;

    @Override
    protected IGenericRepo<Curso, String> getRepo() {
        return repo;
    }
}
