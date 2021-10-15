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
public class WorkspaceUser extends AbstractEntity {

    @ManyToOne
    private Workspace workspace;

    @ManyToOne
    private User user;

    @ManyToOne
    private WorkspaceRole workspaceRole;

    @Column(nullable = false)
    private Timestamp dateInvited;

    @Column(nullable = false)
    private Timestamp dateJoined;

}
