package campuslifecenter.info.model;

import campuslifecenter.info.entry.Info;

import java.util.List;

public abstract class InfoItem extends Info {

    private int order;
    private List<String> value;
    private String aid;
    private String accountName;

    @SuppressWarnings({"AlibabaSwitchStatement", "unchecked"})
    public static  <T extends InfoItem> T create(Info info) {
        InfoItem item = null;
        switch (info.getType()) {
            case 0 -> item = new TextItem();
            case 1 -> item = new CompositeItem();
            case 2 -> item = new RadioItem();
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

    public InfoItem setOrder(int order) {
        this.order = order;
        return this;
    }

    public List<String> getValue() {
        return value;
    }

    public InfoItem setValue(List<String> value) {
        this.value = value;
        return this;
    }

    public String getAid() {
        return aid;
    }

    public InfoItem setAid(String aid) {
        this.aid = aid;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public InfoItem setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public static class TextItem extends InfoItem {

        private String sample;

        public String getSample() {
            return sample;
        }

        public TextItem setSample(String sample) {
            this.sample = sample;
            return this;
        }
    }

    public static class CompositeItem extends InfoItem {

        private boolean arr;
        private List<InfoItem> items;

        public boolean isArr() {
            return arr;
        }

        public CompositeItem setArr(boolean arr) {
            this.arr = arr;
            return this;
        }

        public List<InfoItem> getItems() {
            return items;
        }

        public CompositeItem setItems(List<InfoItem> items) {
            this.items = items;
            return this;
        }
    }

    public static class RadioItem extends InfoItem {

        private List<String> radio;

        public List<String> getRadio() {
            return radio;
        }

        public RadioItem setRadio(List<String> radio) {
            this.radio = radio;
            return this;
        }
    }
}