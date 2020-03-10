package quixada.npi.springproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void insert(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void delete(Integer id){
        usuarioRepository.delete(id);
    }

    public Usuario update(String email, boolean habilitado, String nome, String password, Curso curso, Integer id){
        Usuario usuario = new Usuario(id, nome, habilitado, email, password, curso);
        return usuarioRepository.save(usuario);
    }
}
