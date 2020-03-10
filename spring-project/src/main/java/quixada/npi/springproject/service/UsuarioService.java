package quixada.npi.springproject.service;


import quixada.npi.springproject.model.Curso;
import quixada.npi.springproject.model.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario findByEmail(String email);

    Usuario find(Integer id);

    List<Usuario> findAll();

    void insert(String email, boolean habilitado, String nome, String password);

    void delete(Integer id);

    int update(String email, boolean habilitado, String nome, String password, Curso curso, Integer id);
}
