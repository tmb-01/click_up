package uz.pdp.click_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.enums.TaskPermission;
import uz.pdp.click_up.entity.template.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ProjectUser extends AbstractEntity {

    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private TaskPermission taskPermission;
}
