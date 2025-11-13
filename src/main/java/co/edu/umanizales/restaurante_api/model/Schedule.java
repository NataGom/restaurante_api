package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Schedule for courses
 * Aggregation: A course can have one or multiple schedules
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Course course; // Reference to the course (optional for this relationship)
    
    /**
     * Constructor without course reference
     */
    public Schedule(long id, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    /**
     * Get duration in minutes
     */
    public long getDurationMinutes() {
        if (startTime != null && endTime != null) {
            return java.time.Duration.between(startTime, endTime).toMinutes();
        }
        return 0;
    }
    
    /**
     * Check if schedule overlaps with another
     */
    public boolean overlapsWith(Schedule other) {
        if (other == null || !this.dayOfWeek.equals(other.dayOfWeek)) {
            return false;
        }
        return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
    }
    
    @Override
    public String toString() {
        return dayOfWeek + " " + startTime + " - " + endTime;
    }
}
