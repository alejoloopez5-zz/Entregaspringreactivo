package co.com.alejoloopez5.entrega.service.impl;

import co.com.alejoloopez5.entrega.document.Matricula;
import co.com.alejoloopez5.entrega.repo.IGenericRepo;
import co.com.alejoloopez5.entrega.repo.IMatriculaRepo;
import co.com.alejoloopez5.entrega.service.IMatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatriculaServiceImpl extends CrudImpl<Matricula,String> implements IMatriculaService {

    @Autowired
    private IMatriculaRepo repo;

    @Override
    protected IGenericRepo<Matricula, String> getRepo() {
        return repo;
    }
}
