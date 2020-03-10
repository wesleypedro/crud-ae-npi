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
    @Override
    Usuario getOne(Integer id);

    // Find all
    @Query("SELECT new Usuario(u.id, u.nome, u.habilitado, u.email, u.password) FROM Usuario u")
    List<Usuario> findAll();

    // Insert users
    @Modifying
    @Query(value = "INSERT INTO Usuario (email, habilitado, nome , password) " +
            "VALUES (:email, :habilitado, :nome, :password)",
            nativeQuery = true)
    @Transactional
    void insert(@Param("email") String email, @Param("habilitado") boolean habilitado,
               @Param("nome") String nome, @Param("password") String password);

    // Delete user
    @Modifying
    @Query("DELETE FROM Usuario u WHERE u.id=:id AND u.habilitado=false")
    @Transactional
    void delete(@Param("id") Integer id);

    // Update user
//    @Modifying
//    @Query("UPDATE Usuario SET email=:email, habilitado=:habilitado, nome=:nome, password=:password " +
//            "curso=:curso.getId WHERE id=:id")
//    @Transactional
//    int update(@Param("email") String email, @Param("habilitado") boolean habilitado, @Param("nome") String nome,
//               @Param("password") String password, @Param("curso") Curso curso, @Param("id") Integer id);
}
