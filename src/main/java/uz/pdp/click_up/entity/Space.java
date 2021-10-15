package uz.pdp.click_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.template.AbstractEntity;
import uz.pdp.click_up.entity.template.Icon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Space extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne(optional = false)
    private Workspace workspace;

    @Column(nullable = false)
    private String initialLetter;

    @OneToOne
    private Icon icon;

    @OneToOne
    private Attachment avatar;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private String accessType;

}
