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
public class WorkspaceRole extends AbstractEntity {

    @ManyToOne
    private Workspace workspace;

    @Column
    private String name;

    @ManyToOne
    private WorkspaceRole extendsRole;

}
