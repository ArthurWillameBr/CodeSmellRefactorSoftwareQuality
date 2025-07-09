package org.example.studyplanner;

import java.util.List;
import java.util.Map;

public class KanbanRenderer {
    private final Map<KanbanView.State, List<PlannerMaterial>> kanban;

    public KanbanRenderer(Map<KanbanView.State, List<PlannerMaterial>> kanban) {
        this.kanban = kanban;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ Material ToDo:\n");
        appendMaterials(sb, KanbanView.State.TODO);

        sb.append("\nMaterial in progress:\n");
        appendMaterials(sb, KanbanView.State.DOING);

        sb.append("\nMaterial completed:\n");
        appendMaterials(sb, KanbanView.State.DONE);

        sb.append("]");
        return sb.toString();
    }
    private void appendMaterials(StringBuilder sb, KanbanView.State state) {
        List<PlannerMaterial> materials = kanban.get(state);
        if (materials == null || materials.isEmpty()) {
            sb.append("No material found");
            sb.append(System.lineSeparator()); // <-- Adiciona quebra de linha após a mensagem
        } else {
            for (PlannerMaterial material : materials) {
                sb.append(", ").append(material.toString());
            }
            sb.append(System.lineSeparator()); // <-- Também quebra linha após listar materiais
        }
    }

}
