package fh2229.com.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by fushiyong on 2018/2/8.
 */
@Setter
@Getter
public class ValidateData {

    @JsonProperty("Result")
    private String result ;

    @JsonProperty("Id")
    private String id;


}
