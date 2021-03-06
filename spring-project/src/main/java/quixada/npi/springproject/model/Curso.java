package quixada.npi.springproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Curso {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String nome;

    private String sigla;

    private String turno;

    @JsonIgnore
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    public Curso() {
    }

    public Curso(Integer id, @NotEmpty String nome, String sigla, String turno) {
        this(id, nome, sigla, turno, null);
    }

    public Curso(Integer id, @NotEmpty String nome, String sigla, String turno, List<Usuario> usuarios) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.turno = turno;
        this.usuarios = usuarios;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void addUsuario(Usuario usuario){
        usuarios.add(usuario);
    }
}
