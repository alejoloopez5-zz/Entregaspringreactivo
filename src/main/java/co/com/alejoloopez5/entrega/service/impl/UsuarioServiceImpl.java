package co.com.alejoloopez5.entrega.service.impl;

import co.com.alejoloopez5.entrega.document.Usuario;
import co.com.alejoloopez5.entrega.repo.IGenericRepo;
import co.com.alejoloopez5.entrega.repo.IRolRepo;
import co.com.alejoloopez5.entrega.repo.IUsuarioRepo;
import co.com.alejoloopez5.entrega.security.User;
import co.com.alejoloopez5.entrega.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Service
public class UsuarioServiceImpl extends CrudImpl<Usuario,String> implements IUsuarioService{

    @Autowired
    private IUsuarioRepo repo;

    @Autowired
    private IRolRepo rolRepo;

    @Override
    protected IGenericRepo<Usuario, String> getRepo() {
        return repo;
    }

    @Override
    public Mono<User> buscarPorUsuario(String usuario) {
        Mono<Usuario> monoUsuario = repo.findOneByUsuario(usuario);

        List<String> roles = new ArrayList<String>();

        return monoUsuario.flatMap(u -> {
            return Flux.fromIterable(u.getRoles())
                    .flatMap(rol -> {
                        return rolRepo.findById(rol.getId())
                                .map(r -> {
                                    roles.add(r.getNombre());
                                    return r;
                                });
                    }).collectList().flatMap(list -> {
                        u.setRoles(list);
                        return Mono.just(u);
                    });
        })
                .flatMap(u -> {
                    return Mono.just(new User(u.getUsuario(), u.getClave(), u.getEstado(), roles));
                });
    }
}
