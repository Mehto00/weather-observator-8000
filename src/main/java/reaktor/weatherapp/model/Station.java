package reaktor.weatherapp.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Station {

    // ======== Properties: ======== //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATION_ID")
    private Long StationId; // Automatically generated station Id number

    @Column(name = "STATION_NAME")
    private String StationName;

    // ======== Constructors: ======== //

    public Station(String stationName) {
        StationName = stationName;
    }

    // ======== Getters / Setters: ======== //

    public Long getStationId() {
        return StationId;
    }

    public String getStationName() {
        return StationName;
    }

    // ======== toString: ======== //

    @Override
    public String toString() {
        return "Station{" +
                "StationId=" + StationId +
                ", StationName='" + StationName + '\'' +
                '}';
    }

    // Initializing the pre-given default stations
    public static void main(String[] args) {
        Station tokyo = new Station("Tokyo");
        Station helsinki = new Station("Helsinki");
        Station new_york  = new Station("New York");
        Station amsterdam = new Station("Amsterdam");
        Station dubai = new Station("Dubai");
    }
}
