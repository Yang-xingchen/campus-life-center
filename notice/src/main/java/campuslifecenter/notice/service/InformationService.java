package campuslifecenter.notice.service;

import campuslifecenter.common.model.Response;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@FeignClient(value = "info", path = "/info", contextId = "info")
public interface InformationService {

    @PostMapping("/addCollect")
    Response<String> addInfoCollect(AddInfoRequest informationList);

    @PostMapping("/{id}/select")
    Response<List<String>> select(@PathVariable("id") long id, @RequestParam int type, @RequestParam String text);

    @GetMapping("/ref/{ref}/name")
    Response<String> getRefName(@RequestParam String ref);

    @PostMapping("/ref/{ref}/updateAccount")
    Response<Boolean> updateAccount(@PathVariable("ref") String ref, @RequestBody List<String> aids);

    class AddInfoRequest extends InfoCollectRequest {

        @ApiModelProperty("列表")
        private List<String> aids;

        public List<String> getAids() {
            return aids;
        }

        public AddInfoRequest setAids(List<String> aids) {
            this.aids = aids;
            return this;
        }
    }

    class InfoCollectRequest implements Serializable {
        @ApiModelProperty("是否已存在")
        private boolean exist;
        @ApiModelProperty("存在时信息id")
        private long id;

        @ApiModelProperty("是否允许多个")
        private boolean multiple;
        @ApiModelProperty("默认可见性")
        private int defaultVisibility;
        @ApiModelProperty("类型")
        private int type;
        @ApiModelProperty("名称")
        private String name;

        @ApiModelProperty("类型为文本时示例")
        private String sample;
        @ApiModelProperty("文本类型")
        private Integer textType;
        @ApiModelProperty("正则内容")
        private String regular;

        @ApiModelProperty("类型为组合时内容")
        private List<InfoCollectRequest> compositeInfo;
        @ApiModelProperty("序号")
        private int order;

        @ApiModelProperty("类型为单选时选项")
        private List<String> radioInfo;

        @ApiModelProperty("类型为文件时的存储路径")
        private String path;


        public boolean isExist() {
            return exist;
        }

        public InfoCollectRequest setExist(boolean exist) {
            this.exist = exist;
            return this;
        }

        public long getId() {
            return id;
        }

        public InfoCollectRequest setId(long id) {
            this.id = id;
            return this;
        }

        public boolean isMultiple() {
            return multiple;
        }

        public InfoCollectRequest setMultiple(boolean multiple) {
            this.multiple = multiple;
            return this;
        }

        public int getDefaultVisibility() {
            return defaultVisibility;
        }

        public InfoCollectRequest setDefaultVisibility(int defaultVisibility) {
            this.defaultVisibility = defaultVisibility;
            return this;
        }

        public int getType() {
            return type;
        }

        public InfoCollectRequest setType(int type) {
            this.type = type;
            return this;
        }

        public String getName() {
            return name;
        }

        public InfoCollectRequest setName(String name) {
            this.name = name;
            return this;
        }

        public String getSample() {
            return sample;
        }

        public InfoCollectRequest setSample(String sample) {
            this.sample = sample;
            return this;
        }

        public Integer getTextType() {
            return textType;
        }

        public InfoCollectRequest setTextType(Integer textType) {
            this.textType = textType;
            return this;
        }

        public String getRegular() {
            return regular;
        }

        public InfoCollectRequest setRegular(String regular) {
            this.regular = regular;
            return this;
        }

        public List<InfoCollectRequest> getCompositeInfo() {
            return compositeInfo;
        }

        public InfoCollectRequest setCompositeInfo(List<InfoCollectRequest> compositeInfo) {
            this.compositeInfo = compositeInfo;
            return this;
        }

        public int getOrder() {
            return order;
        }

        public InfoCollectRequest setOrder(int order) {
            this.order = order;
            return this;
        }

        public List<String> getRadioInfo() {
            return radioInfo;
        }

        public InfoCollectRequest setRadioInfo(List<String> radioInfo) {
            this.radioInfo = radioInfo;
            return this;
        }

        public String getPath() {
            return path;
        }

        public InfoCollectRequest setPath(String path) {
            this.path = path;
            return this;
        }
    }
}
