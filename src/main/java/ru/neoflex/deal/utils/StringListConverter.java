package ru.neoflex.deal.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Component
public class StringListConverter {
    private static final ObjectMapper objectMapper = JsonMapper.builder().build().registerModule(new JavaTimeModule());

    public static String listToString(List<Object> objectList) {
        try {
            StringBuilder result = new StringBuilder();

            for (Object o : objectList) {
                result.append(objectMapper.writeValueAsString(o)).append(";");
            }

            return result.toString();
        } catch (JsonProcessingException e) {
            log.warn("ApplicationStatusHistoryConverter - convertor error listToString - objectList {}", objectList);
            return null;
        }
    }

    public static List<Object> stringToList(String objectString, Object object) {
        try {
            List<Object> objectList = new ArrayList<>();

            String[] substrings = objectString.split(";");
            for (String substring : substrings) {
                Object o = objectMapper.readValue(substring, object.getClass());
                objectList.add(o);
            }

            return objectList;
        } catch (JsonProcessingException e) {
            log.warn("ApplicationStatusHistoryConverter - convertor error stringToList - objectString {}", objectString);
            return new ArrayList<>();
        }
    }
}