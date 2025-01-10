package org.book_my_show.services;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.dto.TheatreDTO;
import org.book_my_show.mappers.TheatreMapper;
import org.book_my_show.repo.TheatreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreMapper theatreMapper;
    private final TheatreRepository theatreRepository;

    public TheatreDTO createTheatre(TheatreDTO theatreDTO){
        Theatre theatre = theatreMapper.createTheatre(theatreDTO);
        theatre = theatreRepository.save(theatre);
        return theatreMapper.toDto(theatre);
    }

    public static <T> String toJson(T data) {
        if(data!=null){
            try {
                ObjectMapper mapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                mapper.writerWithDefaultPrettyPrinter();
                return mapper.writeValueAsString(data);
            }catch (JsonProcessingException e){
                System.out.println("Location:[" + e.getLocation() + ", message:[" + e.getMessage() + "]");
            }

        }
        return "";
    }
}
