package campuslifecenter.info.model;

import campuslifecenter.info.entry.Info;
import campuslifecenter.info.entry.InfoText;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class AddInfoRequest implements Serializable {

    @ApiModelProperty("列表")
    private List<String> aids;

    @ApiModelProperty("收集项目")
    private InfoCollect info;

    public static class InfoCollect {
        @ApiModelProperty("是否已存在")
        private boolean exist;
        @ApiModelProperty("存在时信息id")
        private long id;

        @ApiModelProperty("是否允许多个")
        private boolean multiple;
        @ApiModelProperty("是否允许其他收集使用")
        private boolean persistent;
        @ApiModelProperty("默认可见性")
        private int defaultVisibility;
        @ApiModelProperty("序号")
        private int order;
        @ApiModelProperty("类型")
        private int type;
        @ApiModelProperty("名称")
        private String name;

        @ApiModelProperty("类型为文本时示例")
        private String textInfoSample;

        @ApiModelProperty("类型为组合时内容")
        private List<InfoCollect> compositeInfo;

        @ApiModelProperty("类型为单选时选项")
        private List<String> radioInfo;

        public Info toInfo() {
            return new Info()
                    .withName(name)
                    .withType(type)
                    .withMultiple(multiple)
                    .withDefaultVisibility(defaultVisibility);
        }

        public InfoText toText() {
            if (getType() != 0) {
                throw new IllegalArgumentException("illegal info type.");
            }
            return new InfoText().withSample(textInfoSample).withId(id);
        }

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

        public boolean isMultiple() {
            return multiple;
        }

        public InfoCollect setMultiple(boolean multiple) {
            this.multiple = multiple;
            return this;
        }

        public boolean isPersistent() {
            return persistent;
        }

        public InfoCollect setPersistent(boolean persistent) {
            this.persistent = persistent;
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

        public List<InfoCollect> getCompositeInfo() {
            return compositeInfo;
        }

        public InfoCollect setCompositeInfo(List<InfoCollect> compositeInfo) {
            this.compositeInfo = compositeInfo;
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

    public InfoCollect getInfo() {
        return info;
    }

    public AddInfoRequest setInfo(InfoCollect info) {
        this.info = info;
        return this;
    }
}
