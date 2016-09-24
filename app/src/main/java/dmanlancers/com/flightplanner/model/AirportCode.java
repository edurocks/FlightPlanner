package dmanlancers.com.flightplanner.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AirportCode extends RealmObject {

    @PrimaryKey
    private int id;
    private String airportCode;

    public AirportCode() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
}
