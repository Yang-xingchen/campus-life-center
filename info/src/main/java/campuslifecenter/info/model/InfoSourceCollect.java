package campuslifecenter.info.model;

import java.io.Serializable;
import java.util.List;

public class InfoSourceCollect implements Serializable {

    private String source;
    private List<InfoItem> items;

    public String getSource() {
        return source;
    }

    public InfoSourceCollect setSource(String source) {
        this.source = source;
        return this;
    }

    public List<InfoItem> getItems() {
        return items;
    }

    public InfoSourceCollect setItems(List<InfoItem> items) {
        this.items = items;
        return this;
    }
}
