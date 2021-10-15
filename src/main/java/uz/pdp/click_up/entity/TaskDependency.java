package uz.pdp.click_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.enums.DependencyTask;
import uz.pdp.click_up.entity.template.AbstractEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TaskDependency extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Task task;

    @Column(nullable = false)
    private Long dependencyTaskId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DependencyTask dependencyTask;

    
}
