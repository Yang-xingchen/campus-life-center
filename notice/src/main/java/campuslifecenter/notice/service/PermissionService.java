package campuslifecenter.notice.service;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.List;

public interface PermissionService {

    @GetMapping("/{aid}/{oid}")
    List<Permission> getPermission(@PathVariable("aid") String aid, @PathVariable("oid") int oid);

    class Permission implements Serializable {
        @ApiModelProperty(value = "id")
        private Integer id;

        @ApiModelProperty(value = "类型: 0, 组织管理; 1, 成员管理; 2. 通知管理")
        private Integer type;

        @ApiModelProperty(value = "名称")
        private String name;

        private static final long serialVersionUID = 1L;

        public Integer getId() {
            return id;
        }

        public Permission withId(Integer id) {
            this.setId(id);
            return this;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getType() {
            return type;
        }

        public Permission withType(Integer type) {
            this.setType(type);
            return this;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public Permission withName(String name) {
            this.setName(name);
            return this;
        }

        public void setName(String name) {
            this.name = name == null ? null : name.trim();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName());
            sb.append(" [");
            sb.append("Hash = ").append(hashCode());
            sb.append(", id=").append(id);
            sb.append(", type=").append(type);
            sb.append(", name=").append(name);
            sb.append("]");
            return sb.toString();
        }
    }
}
