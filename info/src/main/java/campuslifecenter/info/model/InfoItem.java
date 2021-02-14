package campuslifecenter.info.model;

import campuslifecenter.info.entry.Info;

import java.util.List;
import java.util.stream.Collectors;

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
            case 3 -> item = new FileItem();
            default -> throw new IllegalArgumentException("type is undefined: id=" + info.getId());
        }
        item.withId(info.getId())
                .withType(info.getType())
                .withName(info.getName())
                .withMultiple(info.getMultiple())
                .withHide(info.getHide())
                .withDefaultVisibility(info.getDefaultVisibility());
        return (T)item;
    }

    public abstract InfoItem toInfo();

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

        private String regular;

        private int textType;

        @Override
        public TextItem toInfo() {
            TextItem item = new TextItem();
            item.setSample(sample)
                    .setTextType(textType)
                    .setRegular(regular)
                    .withId(getId())
                    .withType(getType())
                    .withName(getName())
                    .withMultiple(getMultiple())
                    .withDefaultVisibility(getDefaultVisibility())
                    .withHide(getHide());
            return item;
        }

        public String getSample() {
            return sample;
        }

        public TextItem setSample(String sample) {
            this.sample = sample;
            return this;
        }

        public String getRegular() {
            return regular;
        }

        public TextItem setRegular(String regular) {
            this.regular = regular;
            return this;
        }

        public int getTextType() {
            return textType;
        }

        public TextItem setTextType(int textType) {
            this.textType = textType;
            return this;
        }
    }

    public static class CompositeItem extends InfoItem {

        private boolean arr;
        private List<InfoItem> items;

        @Override
        public CompositeItem toInfo() {
            CompositeItem item = new CompositeItem();
            item.setArr(arr)
                    .setItems(items.stream().map(InfoItem::toInfo).collect(Collectors.toList()))
                    .withId(getId())
                    .withType(getType())
                    .withName(getName())
                    .withMultiple(getMultiple())
                    .withDefaultVisibility(getDefaultVisibility())
                    .withHide(getHide());
            return item;
        }
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

        @Override
        public RadioItem toInfo() {
            RadioItem item = new RadioItem();
            item.setRadio(radio)
                    .withId(getId())
                    .withType(getType())
                    .withName(getName())
                    .withMultiple(getMultiple())
                    .withDefaultVisibility(getDefaultVisibility())
                    .withHide(getHide());
            return item;
        }

        public List<String> getRadio() {
            return radio;
        }

        public RadioItem setRadio(List<String> radio) {
            this.radio = radio;
            return this;
        }
    }

    public static class FileItem extends InfoItem {

        private String path;

        @Override
        public FileItem toInfo() {
            FileItem item = new FileItem();
            item.setPath(path)
                    .withId(getId())
                    .withType(getType())
                    .withName(getName())
                    .withMultiple(getMultiple())
                    .withDefaultVisibility(getDefaultVisibility())
                    .withHide(getHide());
            return null;
        }

        public String getPath() {
            return path;
        }

        public FileItem setPath(String path) {
            this.path = path;
            return this;
        }
    }
}