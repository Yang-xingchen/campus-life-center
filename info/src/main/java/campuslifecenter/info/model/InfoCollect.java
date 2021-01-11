package campuslifecenter.info.model;

import campuslifecenter.info.entry.Info;

import java.io.Serializable;
import java.util.List;

public class InfoCollect implements Serializable {

    private String source;
    private List<InfoCollectItem> items;

    public static class InfoCollectItem extends Info {

        private int order;
        private String value;
        private String aid;
        private String accountName;

        @SuppressWarnings({"AlibabaSwitchStatement", "unchecked"})
        public static  <T extends InfoCollectItem> T create(Info info) {
            InfoCollectItem item= null;
            switch (info.getType()) {
                case 0 -> item = new TextCollectItem();
                case 1 -> item = new ArrayCollectItem();
                case 2 -> item = new RadioCollectItem();
                default -> throw new IllegalArgumentException("type is undefined: id=" + info.getId());
            }
            item.withId(info.getId())
                    .withType(info.getType())
                    .withName(info.getName())
                    .withDefaultVisibility(info.getDefaultVisibility());
            return (T)item;
        }

        public int getOrder() {
            return order;
        }

        public InfoCollectItem setOrder(int order) {
            this.order = order;
            return this;
        }

        public String getValue() {
            return value;
        }

        public InfoCollectItem setValue(String value) {
            this.value = value;
            return this;
        }

        public String getAid() {
            return aid;
        }

        public InfoCollectItem setAid(String aid) {
            this.aid = aid;
            return this;
        }

        public String getAccountName() {
            return accountName;
        }

        public InfoCollectItem setAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }
    }

    public static class TextCollectItem extends InfoCollectItem {

        private String sample;

        public String getSample() {
            return sample;
        }

        public TextCollectItem setSample(String sample) {
            this.sample = sample;
            return this;
        }
    }

    public static class ArrayCollectItem extends InfoCollectItem {

        private List<InfoCollectItem> items;

        public List<InfoCollectItem> getItems() {
            return items;
        }

        public ArrayCollectItem setItems(List<InfoCollectItem> items) {
            this.items = items;
            return this;
        }
    }

    public static class RadioCollectItem extends InfoCollectItem {

        private List<String> radio;

        public List<String> getRadio() {
            return radio;
        }

        public RadioCollectItem setRadio(List<String> radio) {
            this.radio = radio;
            return this;
        }
    }

    public String getSource() {
        return source;
    }

    public InfoCollect setSource(String source) {
        this.source = source;
        return this;
    }

    public List<InfoCollectItem> getItems() {
        return items;
    }

    public InfoCollect setItems(List<InfoCollectItem> items) {
        this.items = items;
        return this;
    }
}
