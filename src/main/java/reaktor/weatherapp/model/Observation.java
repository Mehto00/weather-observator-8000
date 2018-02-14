package reaktor.weatherapp.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Observation {

    // ======== Properties: ======== //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OBSERVATION_ID")
    private Long observationId; // Automatically generated observation Id number

    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @Column(name = "TEMPERATURE")
    private double temperature;

    @Column(name = "STATION")
    private Station station;

    private String formatedDate;

    // ======== Constructors: ======== //

    public Observation() {}

    public Observation(Date timestamp, double temperature, Station station) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.station = station;
    }

    public Observation(Date timestamp, double temperature, Station station, String formatedDate) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.station = station;
        this.formatedDate = formatedDate;
    }

    // ======== Getters / Setters: ======== //

    public Long getPerceptionId() {
        return observationId;
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

    public void setFormatedDate(Date timestamp) {
        String pattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        this.formatedDate = df.format(timestamp);
    }

    // ======== toString: ======== //

    @Override
    public String toString() {
        return "{" +
                "\"observationId\" : \"" + observationId + "\"," +
                "\"timestamp\" : \"" + formatedDate + "\"," +
                "\"temperature\" : \"" + temperature + "\"," +
                "\"station\" : \"" + station + "\"" +
                '}';
    }
}
