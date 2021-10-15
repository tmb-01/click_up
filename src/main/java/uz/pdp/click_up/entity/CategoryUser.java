package uz.pdp.click_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.enums.TaskPermission;
import uz.pdp.click_up.entity.template.AbstractEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CategoryUser extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TaskPermission taskPermission;

}
