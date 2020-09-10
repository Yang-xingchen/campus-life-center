package campuslifecenter.notice.model;

import campuslifecenter.common.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Notice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany
    @JoinTable(
            name = "authors_notice",
            joinColumns = @JoinColumn(name = "nid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "uid", referencedColumnName = "id")
    )
    private List<User> authors;
    private String authorName;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "notice")
    private List<InformsUser> informs;

}
