package com.example.creskill.Repostory;

import com.example.creskill.Model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork,Integer> {
    Artwork findArtworkByArtworkId (Integer artworkId);

    List<Artwork> findArtworkByUserid (Integer userid);

    @Query("select count (a) from Artwork a where a.userid=?1")
    Integer countByUserid (Integer userid );

    @Query("select a from Artwork a where a.likes>0 order by a.likes desc")
    List<Artwork> tobArtwork ();
}
