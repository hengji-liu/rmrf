package bean;

/**
 * Created by liquan on 26/09/2016.
 */
public class GraphEdge {
    private GraphNode from;
    private GraphNode to;
    private String id;
    private String label;

    public GraphEdge() {
        super();
    }

    public GraphNode getFrom() {
        return from;
    }

    public void setFrom(GraphNode from) {
        this.from = from;
    }

    public GraphNode getTo() {
        return to;
    }

    public void setTo(GraphNode to) {
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphEdge)) return false;

        GraphEdge graphEdge = (GraphEdge) o;

        return id != null ? id.equals(graphEdge.id) : graphEdge.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "{" +
                "from:\"" + from.getId() +
                "\", to:\"" + to.getId() +
                "\", label:\"" + label +
                "\"}";
    }
}
