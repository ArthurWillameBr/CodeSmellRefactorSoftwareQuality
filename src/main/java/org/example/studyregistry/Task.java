package org.example.studyregistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task extends Registry {
    private String title;
    private String description;
    private String author;
    private LocalDateTime date;

    public Task(String title, String description, String author, LocalDateTime date) {
        this.title = title;
        this.name = title;
        this.description = description;
        this.author = author;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.name = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the date formatted as dd/MM/yyyy HH:mm
     */
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(formatter);
    }

    /**
     * Updates the task's date to the current date and time
     */
    public void updateDateToNow() {
        this.date = LocalDateTime.now();
    }

    /**
     * Returns a one-line summary of the task: title, author, and formatted date
     */
    public String getSummary() {
        return String.format("Task: %s\nAuthor: %s\nDate: %s", title, author, getFormattedDate());
    }
}
