package co.com.alejoloopez5.entrega.service;

import co.com.alejoloopez5.entrega.document.Usuario;
import co.com.alejoloopez5.entrega.security.User;
import reactor.core.publisher.Mono;


public interface IUsuarioService extends ICrud<Usuario,String>{

    Mono<User> buscarPorUsuario(String usuario);
}
