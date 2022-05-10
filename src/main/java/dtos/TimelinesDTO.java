package dtos;

import entities.Timeline;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class TimelinesDTO {

    List<TimelineDTO> timelines = new ArrayList<>();

    public TimelinesDTO(List<Timeline> entity) {
        entity.forEach((timeline -> {
            timelines.add(new TimelineDTO(timeline));
        }));
    }

    public List<TimelineDTO> getTimelines() {
        return timelines;
    }

    @Override
    public String toString() {
        return "TimelinesDTO{" +
                "timelines=" + timelines +
                '}';
    }
}