package quixada.npi.springproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quixada.npi.springproject.model.Curso;
import quixada.npi.springproject.model.Usuario;
import quixada.npi.springproject.repository.CursoRepository;
import quixada.npi.springproject.service.CursoService;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void insert(String nome, String sigla, String turno) {
        cursoRepository.insert(nome, sigla, turno);
    }

    @Override
    public Curso find(Integer id) {
        return cursoRepository.getOne(id);
    }

    @Override
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso update(String nome, String sigla, String turno, List<Usuario> usuarios, Integer id) {
        Curso curso = new Curso(id, nome, sigla, turno, usuarios);
        return cursoRepository.save(curso);
    }

    @Override
    public void delete(Integer id) {
        cursoRepository.delete(id);
    }
}
