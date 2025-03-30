package net.engineeringdigest.journalApp.api.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponse {


    private Current current;
    @Data
    public class Current {

        @JsonProperty("temp_c")
        private double tempC;
    }
}

