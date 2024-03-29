package campuslifecenter.notice.service;

import campuslifecenter.common.model.Response;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@FeignClient(name = "user-center", path = "/organization", contextId = "organization")
public interface OrganizationService {

    @GetMapping("{id}/memberId")
    Response<List<String>> getMemberId(@PathVariable("id") int id);

    @GetMapping("/{id}")
    Response<Organization> getOrganization(@PathVariable("id") int id);

    class Organization implements Serializable {
        @ApiModelProperty(value = "id")
        private Integer id;

        @ApiModelProperty(value = "id")
        private Integer parent;

        private String type;

        private String creator;

        private String name;

        private Integer visibility;

        private Date createData;

        private static final long serialVersionUID = 1L;

        public Integer getId() {
            return id;
        }

        public Organization withId(Integer id) {
            this.setId(id);
            return this;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getParent() {
            return parent;
        }

        public Organization withParent(Integer parent) {
            this.setParent(parent);
            return this;
        }

        public void setParent(Integer parent) {
            this.parent = parent;
        }

        public String getType() {
            return type;
        }

        public Organization withType(String type) {
            this.setType(type);
            return this;
        }

        public void setType(String type) {
            this.type = type == null ? null : type.trim();
        }

        public String getCreator() {
            return creator;
        }

        public Organization withCreator(String creator) {
            this.setCreator(creator);
            return this;
        }

        public void setCreator(String creator) {
            this.creator = creator == null ? null : creator.trim();
        }

        public String getName() {
            return name;
        }

        public Organization withName(String name) {
            this.setName(name);
            return this;
        }

        public void setName(String name) {
            this.name = name == null ? null : name.trim();
        }

        public Integer getVisibility() {
            return visibility;
        }

        public Organization withVisibility(Integer visibility) {
            this.setVisibility(visibility);
            return this;
        }

        public void setVisibility(Integer visibility) {
            this.visibility = visibility;
        }

        public Date getCreateData() {
            return createData;
        }

        public Organization withCreateData(Date createData) {
            this.setCreateData(createData);
            return this;
        }

        public void setCreateData(Date createData) {
            this.createData = createData;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName());
            sb.append(" [");
            sb.append("Hash = ").append(hashCode());
            sb.append(", id=").append(id);
            sb.append(", parent=").append(parent);
            sb.append(", type=").append(type);
            sb.append(", creator=").append(creator);
            sb.append(", name=").append(name);
            sb.append(", visibility=").append(visibility);
            sb.append(", createData=").append(createData);
            sb.append("]");
            return sb.toString();
        }
    }
}
