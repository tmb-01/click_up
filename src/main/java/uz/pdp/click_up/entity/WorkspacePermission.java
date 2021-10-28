package uz.pdp.click_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.enums.WorkspacePermissionName;
import uz.pdp.click_up.entity.template.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class WorkspacePermission extends AbstractEntity {

    @ManyToOne(optional = false)
    private WorkspaceRole workspaceRole;

    private WorkspacePermissionName permission;

}



