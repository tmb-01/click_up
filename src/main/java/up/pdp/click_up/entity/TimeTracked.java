package up.pdp.click_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TimeTracked extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Task task;

    @Column(nullable = false)
    private Timestamp startAt;

    private Timestamp stoppedAt;

}
