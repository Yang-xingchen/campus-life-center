package campuslifecenter.notice.model;

import java.io.Serializable;
import java.util.List;

public class AddInfoRequest implements Serializable {

    private List<String> aids;

    private List<InfoCollect> infos;

    public static class InfoCollect {
        private boolean exist;
        private long id;

        private int defaultVisibility;

        private int order;
        private int type;
        private String name;

        private String textInfoSample;

        private List<InfoCollect> arrayInfo;

        private List<String> radioInfo;

        public boolean isExist() {
            return exist;
        }

        public InfoCollect setExist(boolean exist) {
            this.exist = exist;
            return this;
        }

        public long getId() {
            return id;
        }

        public InfoCollect setId(long id) {
            this.id = id;
            return this;
        }

        public int getDefaultVisibility() {
            return defaultVisibility;
        }

        public InfoCollect setDefaultVisibility(int defaultVisibility) {
            this.defaultVisibility = defaultVisibility;
            return this;
        }

        public int getOrder() {
            return order;
        }

        public InfoCollect setOrder(int order) {
            this.order = order;
            return this;
        }

        public int getType() {
            return type;
        }

        public InfoCollect setType(int type) {
            this.type = type;
            return this;
        }

        public String getName() {
            return name;
        }

        public InfoCollect setName(String name) {
            this.name = name;
            return this;
        }

        public String getTextInfoSample() {
            return textInfoSample;
        }

        public InfoCollect setTextInfoSample(String textInfoSample) {
            this.textInfoSample = textInfoSample;
            return this;
        }

        public List<InfoCollect> getArrayInfo() {
            return arrayInfo;
        }

        public InfoCollect setArrayInfo(List<InfoCollect> arrayInfo) {
            this.arrayInfo = arrayInfo;
            return this;
        }

        public List<String> getRadioInfo() {
            return radioInfo;
        }

        public InfoCollect setRadioInfo(List<String> radioInfo) {
            this.radioInfo = radioInfo;
            return this;
        }
    }

    public List<String> getAids() {
        return aids;
    }

    public AddInfoRequest setAids(List<String> aids) {
        this.aids = aids;
        return this;
    }

    public List<InfoCollect> getInfos() {
        return infos;
    }

    public AddInfoRequest setInfos(List<InfoCollect> infos) {
        this.infos = infos;
        return this;
    }
}
