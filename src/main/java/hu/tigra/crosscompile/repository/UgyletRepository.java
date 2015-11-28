package hu.tigra.crosscompile.repository;

import hu.tigra.crosscompile.domain.Ugylet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ugylet entity.
 */
public interface UgyletRepository extends JpaRepository<Ugylet,Long> {

}
