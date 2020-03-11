package quixada.npi.springproject.service;


import quixada.npi.springproject.model.Curso;
import quixada.npi.springproject.model.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario findByEmail(String email);

    Usuario find(Integer id);

    List<Usuario> findAll();

    List<Usuario> findUserByCourse(Integer curso_id);

    void insert(Usuario usuario);

    void delete(Integer id);

    Usuario update(String email, boolean habilitado, String nome, String password, Curso curso, Integer id);
}
