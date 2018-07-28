package space.efremov.otusspringlibrary.dao.jdbc;

import org.springframework.stereotype.Service;
import space.efremov.otusspringlibrary.domain.Entity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class EntityToMapConverter {

    private ObjectMapper oMapper = new ObjectMapper();

    public Map<String, Object> convert(Entity entity) {
        return oMapper.convertValue(entity, Map.class);
    }

    public Map<String, Object> getIdParam(Integer id) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return params;
    }

}
