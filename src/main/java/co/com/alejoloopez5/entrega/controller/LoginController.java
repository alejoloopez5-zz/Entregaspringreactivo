package co.com.alejoloopez5.entrega.controller;

import co.com.alejoloopez5.entrega.document.Usuario;
import co.com.alejoloopez5.entrega.security.AuthResponse;
import co.com.alejoloopez5.entrega.security.ErrorLogin;
import co.com.alejoloopez5.entrega.security.JWTUtil;
import co.com.alejoloopez5.entrega.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private IUsuarioService service;

    @GetMapping()
    public Flux<Usuario> user () {
        return service.listar();
    }

    @PostMapping()
    public Mono<ResponseEntity<?>> login(@RequestHeader("usuario") String usuario, @RequestHeader("clave") String clave){
        return service.buscarPorUsuario(usuario)
                .map((userDetails) -> {

                    if(BCrypt.checkpw(clave, userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(userDetails);
                        Date expiracion = jwtUtil.getExpirationDateFromToken(token);

                        return ResponseEntity.ok(new AuthResponse(token, expiracion));
                    }else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorLogin("credenciales incorrectas", new Date()));
                    }
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
