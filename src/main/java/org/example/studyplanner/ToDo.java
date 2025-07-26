package org.example.studyplanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ToDo implements PlannerMaterial {
    private Integer id;
    private String title;
    private String description;
    private int priority;
    private final List<LocalDateTime> executionTimes = new ArrayList<>();

    public ToDo(Integer id, String title, String description, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Atualiza o id do ToDo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Registra a hora de execução do ToDo.
     */
    public void addExecutionTime() {
        executionTimes.add(LocalDateTime.now());
    }

    /**
     * Verifica se o título ou descrição contém o termo de busca.
     */
    public boolean matches(String search) {
        String lower = search.toLowerCase();
        return title.toLowerCase().contains(lower) || description.toLowerCase().contains(lower);
    }

    /**
     * Retorna a representação formatada com prioridade e execuções.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[(Priority:%d) ToDo %d: %s, %s]", priority, id, title, description));
        sb.append("\n");
        if (executionTimes.isEmpty()) {
            sb.append("No tracks found\n");
        } else {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (LocalDateTime time : executionTimes) {
                sb.append(time.format(fmt)).append("\n");
            }
        }
        return sb.toString();
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void increasePriority() {
        this.priority = Math.max(1, this.priority - 1);
    }

    public void decreasePriority() {
        this.priority++;
    }
}