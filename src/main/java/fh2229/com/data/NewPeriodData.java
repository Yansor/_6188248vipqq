package fh2229.com.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by fushiyong on 2018/2/9.
 */
@Getter
@Setter
@Data
public class NewPeriodData {
    private String fpreviousperiod;

    private String fpreviousresult;

    private String fid ;

    private String fnumberofperiod;

    private String fstarttime;

    private String flottostarttime;

    private String fstatus;

    private String fclosetime;

    private String fnextperiod ;

    private String fnextstarttime;

    private String fsettlefid ;

    private String fsettlenumber;

    private String fsettletime;

    @JsonProperty("ServerTime")
    private String serverTime ;

    private String fisstopseles ;

}



