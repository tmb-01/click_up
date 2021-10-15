package uz.pdp.click_up.entity;

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
public class Task extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @ManyToOne
    private Status status;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Priority priority;

    @ManyToOne
    private Task parentTask;

    @Column(nullable = false)
    private Timestamp startedDate;

    @Column(nullable = false)
    private Boolean startedTimeHas;

    @Column(nullable = false)
    private Timestamp dueDate;

    @Column(nullable = false)
    private Boolean startedDateHas;

    @Column(nullable = false)
    private Long estimateTime;

    @Column(nullable = false)
    private Timestamp estimateDate;

}
