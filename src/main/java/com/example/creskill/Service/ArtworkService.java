package com.example.creskill.Service;

import com.example.creskill.ApiRespose.ApiException;
import com.example.creskill.Model.Artwork;
import com.example.creskill.Model.Projects;
import com.example.creskill.Repostory.ArtworkRepository;
import com.example.creskill.Repostory.TalentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkService {
    private final ArtworkRepository artworkRepository;
private final TalentRepository talentRepository;


    public List<Artwork> get (){
        return artworkRepository.findAll();
    }

    public void  add2 (List<Artwork> artworks) {
        for (Artwork a : artworks) {
            if (talentRepository.findTalentByUserid(a.getUserid()) == null) {
                throw new ApiException("user id not found");
            }
            a.setUploadDate(LocalDate.now());
            artworkRepository.save(a);
        }
    }

    public void  add (Artwork artwork){
        if (talentRepository.findTalentByUserid(artwork.getUserid())==null){
            throw new ApiException("user id not found");
        }
        artwork.setUploadDate(LocalDate.now());
        artworkRepository.save(artwork) ;   }

    public void update (Integer artworkId , Artwork artwork){
        Artwork old = artworkRepository.findArtworkByArtworkId(artworkId);

        if (old==null){
            throw new ApiException("artwork not found");
        }
        if (talentRepository.findTalentByUserid(artwork.getUserid())==null){
            throw new ApiException("user id not found");
        }
        old.setTitle(artwork.getTitle());
        old.setDescription(artwork.getDescription());
        old.setCategory(artwork.getCategory());

        artworkRepository.save(old) ;
    }


    public void delete (Integer artworkId ){
        Artwork artwork = artworkRepository.findArtworkByArtworkId(artworkId);
        if (artwork==null){
            throw new ApiException("projectId not found");
        }
        artworkRepository.delete(artwork);
    }

//#23
    public void likeArtwork (Integer artworkId){
        Artwork artwork = artworkRepository.findArtworkByArtworkId(artworkId);
        if (artwork==null){
            throw new ApiException("projectId not found");
        }
        if (artwork.getLikes()==null){
            artwork.setLikes(1);
        }else artwork.setLikes(artwork.getLikes()+1);
        artworkRepository.save(artwork);
    }
//#24
    public List<Artwork> topArtwork (){
        List<Artwork> top = artworkRepository.tobArtwork();
        if (top==null){
            throw new ApiException("not found");
        }
        return top.stream().limit(3).collect(Collectors.toList());
    }

//#25
    public Map<String ,Object> getArtworkAndCountByUser (Integer userid){
        List<Artwork> artworks = artworkRepository.findArtworkByUserid(userid) ;
        Integer count = artworkRepository.countByUserid(userid);
        Map<String ,Object> artworkAndCountByUser = new HashMap<>();
        artworkAndCountByUser.put("Total: ",count);
        artworkAndCountByUser.put("Artworks : ",artworks);
        return artworkAndCountByUser;
    }
}
