package co.com.alejoloopez5.entrega.repo;

import co.com.alejoloopez5.entrega.document.Usuario;
import reactor.core.publisher.Mono;

public interface IUsuarioRepo extends IGenericRepo<Usuario,String>{

    Mono<Usuario> findOneByUsuario(String usuario);

}
