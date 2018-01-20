package reaktor.weatherapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Perception {

    // ======== Properties: ======== //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERCEPTION_ID")
    private Long perceptionId; // Automatically generated answer Id number

    @Column(name = "TIMESTAMP")
    private String timestamp;

    @Column(name = "TEMPERATURE")
    private String temperature;

    @Column(name = "STATION")
    private String station;

    // ======== Constructors: ======== //

    public Perception() {}

    public Perception(String temperature, String station) {
        Date date = new Date();
        this.timestamp = date.toString();
        this.temperature = temperature;
        this.station = station;
    }

    // ======== Getters / Setters: ======== //

    public Long getPerceptionId() {
        return perceptionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getStation() {
        return station;
    }

    // ======== toString: ======== //

    @Override
    public String toString() {
        return "Perception{" +
                "perceptionId=" + perceptionId +
                ", timestamp='" + timestamp + '\'' +
                ", temperature='" + temperature + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
