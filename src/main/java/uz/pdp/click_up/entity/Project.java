package uz.pdp.click_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Project extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Space space;

    @Column(nullable = false)
    private String accessType;

    @Column(nullable = false)
    private boolean archived;

    @Column(nullable = false)
    private String color;

}
