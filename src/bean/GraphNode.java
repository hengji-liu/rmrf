package bean;

/**
 * Created by liquan on 26/09/2016.
 */
public class GraphNode {
    private String id;
    private String label;
    private String type;

    public GraphNode() {
        super();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphNode)) return false;

        GraphNode graphNode = (GraphNode) o;

        return id != null ? id.equals(graphNode.id) : graphNode.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        label = label.replace("\""," ");
        return '{' +
                "id:\"" + id  +
                "\", label:\"" + label +
                "\", group:\"" + type  +
                "\"}";
    }
}
