package quixada.npi.springproject.service;

import quixada.npi.springproject.model.Curso;
import quixada.npi.springproject.model.Usuario;

import java.util.List;

public interface CursoService {

    void insert(String nome, String sigla, String turno);

    Curso find(Integer id);

    List<Curso> findAll();

    int update(String nome, String sigla, String turno, List<Usuario> usuarios, Integer id);

    void delete(Integer id);

}
