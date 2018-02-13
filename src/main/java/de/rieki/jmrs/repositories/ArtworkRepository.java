package de.rieki.jmrs.repositories;

import de.rieki.jmrs.domain.Artwork;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends CrudRepository<Artwork, Long>{

}
