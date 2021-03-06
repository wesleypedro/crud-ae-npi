package quixada.npi.springproject.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import quixada.npi.springproject.model.Curso;
import quixada.npi.springproject.model.Usuario;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Find by email
    Usuario findByEmail(String email);


    // Find by id
    @Query("SELECT u FROM Usuario u WHERE u.id=:id")
    Usuario find(@Param("id") Integer id);

    // Find all
    List<Usuario> findAll();

    // Insert users
    @Modifying
    @Query(value = "INSERT INTO Usuario (email, habilitado, nome , password) " +
            "VALUES (:email, :habilitado, :nome, :password)",
            nativeQuery = true)
    @Transactional
    void insert(@Param("email") String email, @Param("habilitado") boolean habilitado,
               @Param("nome") String nome, @Param("password") String password);

    // Find users with course
    @Query("SELECT u FROM Usuario u WHERE u.curso.id=:curso_id")
    List<Usuario> findUserByCourse(@Param("curso_id") Integer curso_id);

    // Delete user
    @Modifying
    @Query("DELETE FROM Usuario u WHERE u.id=:id AND u.habilitado=false")
    @Transactional
    void delete(@Param("id") Integer id);

}
