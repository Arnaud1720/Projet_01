    package com.arnaud.ia.api.repository;

    import com.arnaud.ia.api.entity.Roles;
    import com.arnaud.ia.api.entity.Utilisateurs;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;
    import java.util.Optional;

    @Repository
    public interface UtilisateurRepository extends JpaRepository<Utilisateurs,Integer> {
        Optional<Utilisateurs> findById(int id);
        void deleteById(int id);
        boolean existsByEmail(String email);
        List<Utilisateurs> findByRole(Roles role);
        Optional<Utilisateurs> findByEmail(String email);

    }
