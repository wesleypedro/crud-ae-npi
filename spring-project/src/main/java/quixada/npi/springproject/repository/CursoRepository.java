package quixada.npi.springproject.repository;

import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import quixada.npi.springproject.model.Curso;
import quixada.npi.springproject.model.Usuario;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    // Insert course
    @Modifying
    @Query(value = "INSERT INTO Curso (nome , sigla, turno) " +
            "VALUES (:nome, :sigla, :turno)",
            nativeQuery = true)
    @Transactional
    void insert(@Param("nome") String nome, @Param("sigla") String sigla, @Param("turno") String turno);

    // Find
    @Override
    Curso getOne(Integer id);

    // Find all
    @Query("SELECT new Curso(c.id, c.nome, c.sigla, c.turno) FROM Curso c")
    List<Curso> findAll();

    // Update course
    @Modifying
    @Query("UPDATE Curso SET nome=:nome, sigla=:sigla, turno=:turno, usuarios=:usuarios WHERE id=:id")
    @Transactional
    int update(@Param("nome") String nome, @Param("sigla") String sigla, @Param("turno") String turno,
               @Param("usuarios") List<Usuario> usuarios, @Param("id") Integer id);

    // Delete course
    @Modifying
    @Query("DELETE FROM Curso c WHERE c.id=:id")
    @Transactional
    void delete(@Param("id") Integer id);
}
