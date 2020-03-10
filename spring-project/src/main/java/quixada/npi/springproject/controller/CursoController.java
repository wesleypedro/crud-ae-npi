package quixada.npi.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quixada.npi.springproject.model.Curso;
import quixada.npi.springproject.service.CursoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @RequestMapping("{id}")
    public ResponseEntity<Curso> find(@PathVariable Integer id){
        return ResponseEntity.ok(cursoService.find(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Curso>> findAll(){
        return ResponseEntity.ok(cursoService.findAll());
    }


    @PostMapping("")
    public ResponseEntity<List<Curso>> create(@RequestBody Curso curso){
        cursoService.insert(curso.getNome(), curso.getSigla(), curso.getTurno());
        return ResponseEntity.ok(cursoService.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<List<Curso>> update(@RequestBody Curso curso){
        cursoService.update(curso.getNome(), curso.getSigla(), curso.getTurno(),
                curso.getUsuarios(), curso.getId());

        return  ResponseEntity.ok(cursoService.findAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<List<Curso>> delete(@PathVariable Integer id){
        if(cursoService.find(id) != null) {
            cursoService.delete(id);
        }
        return ResponseEntity.ok(cursoService.findAll());
    }

}
