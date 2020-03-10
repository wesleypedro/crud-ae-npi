package quixada.npi.springproject.controller;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import quixada.npi.springproject.model.Usuario;
import quixada.npi.springproject.service.CursoService;
import quixada.npi.springproject.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> find(@PathVariable Integer id) {
        // Busca usuário pelo id e retornar usuário...
        return ResponseEntity.ok(usuarioService.find(id));
    }

    @PostMapping("")
    public ResponseEntity<List<Usuario>> create(@RequestBody Usuario usuario) {
        // Cadastrar usuário e retornar usuário cadastrado...


        System.out.println(usuario.getEmail());
        if(usuarioService.findByEmail(usuario.getEmail()) == null){
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioService.insert(usuario);
        }

        return ResponseEntity.ok(usuarioService.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<List<Usuario>> delete(@PathVariable Integer id){
        if(usuarioService.find(id) != null) {
            usuarioService.delete(id);
        }
        return ResponseEntity.ok(usuarioService.findAll());
    }


    @PutMapping("{id}")
    public ResponseEntity<List<Usuario>> update(@RequestBody Usuario usuario) {

        if(usuario.getPassword().trim().isEmpty()){
            usuario.setPassword(usuarioService.find(usuario.getId()).getPassword());
        }else {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        usuarioService.update(usuario.getEmail(), usuario.isHabilitado(), usuario.getNome(),
                usuario.getPassword(), usuario.getCurso(), usuario.getId());
            return ResponseEntity.ok(usuarioService.findAll());
    }



}
