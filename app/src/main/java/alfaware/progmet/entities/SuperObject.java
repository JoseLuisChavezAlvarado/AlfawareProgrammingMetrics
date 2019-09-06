package alfaware.progmet.entities;

import java.io.Serializable;

public class SuperObject implements Serializable {

    private String id;
    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
