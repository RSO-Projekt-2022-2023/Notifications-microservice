package si.fri.rso.notifications.models.entities;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notifications")
@NamedQueries(value =
        {
                @NamedQuery(name = "NotificationEntity.getAll",
                        query = "SELECT no FROM NotificationEntity no")
        })
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private Instant created;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }


}