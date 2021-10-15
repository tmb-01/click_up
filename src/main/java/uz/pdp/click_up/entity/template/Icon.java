package uz.pdp.click_up.entity.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.Attachment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Icon extends AbstractEntity {

    @OneToOne
    private Attachment attachment;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String initialLetter;

    @Column(nullable = false)
    private String icon;

}
