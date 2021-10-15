package uz.pdp.click_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.enums.StatusType;
import uz.pdp.click_up.entity.template.AbstractEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Status extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Space space;

    @ManyToOne(optional = false)
    private Project project;

    @ManyToOne(optional = false)
    private Category category;

    @Column(nullable = false)
    private String color;

    @Enumerated(value = EnumType.STRING)
    private StatusType statusType;


}
