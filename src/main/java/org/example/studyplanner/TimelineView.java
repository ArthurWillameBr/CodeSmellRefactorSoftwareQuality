package org.example.studyplanner;

import java.time.LocalDateTime;
import java.util.List;

public class TimelineView {

    public String habitDateViewAll(HabitTracker ht) {
        // Just delegate — no more looping into ht’s internals!
        return ht.formatAllHabitsWithDates();
    }

}
