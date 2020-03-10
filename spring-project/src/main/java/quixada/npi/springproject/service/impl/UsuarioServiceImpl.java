package quixada.npi.springproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quixada.npi.springproject.model.Curso;
import quixada.npi.springproject.model.Usuario;
import quixada.npi.springproject.repository.UsuarioRepository;
import quixada.npi.springproject.service.UsuarioService;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario find(Integer id) {
        return usuarioRepository.getOne(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public void insert(String email, boolean habilitado, String nome, String password) {
        usuarioRepository.insert(email, habilitado, nome, password);
    }

    public void delete(Integer id){
        usuarioRepository.delete(id);
    }

    public int update(String email, boolean habilitado, String nome, String password, Curso curso, Integer id){
        return usuarioRepository.update(email, habilitado, nome, password, curso, id);
    }
}
