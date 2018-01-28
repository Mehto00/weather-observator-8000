package reaktor.weatherapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Perception {

    // ======== Properties: ======== //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERCEPTION_ID")
    private Long perceptionId; // Automatically generated perception Id number

    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @Column(name = "TEMPERATURE")
    private double temperature;

    @Column(name = "STATION")
    private Station station;

    // ======== Constructors: ======== //

    public Perception() {}

    public Perception(Date timestamp, double temperature, Station station) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.station = station;
    }

    // ======== Getters / Setters: ======== //

    public Long getPerceptionId() {
        return perceptionId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    // ======== toString: ======== //

    @Override
    public String toString() {
        return "{" +
                "\"perceptionId\" : \"" + perceptionId + "\"," +
                "\"timestamp\" : \"" + timestamp + "\"," +
                "\"temperature\" : \"" + temperature + "\"," +
                "\"station\" : \"" + station + "\"" +
                '}';
    }
}
