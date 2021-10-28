package uz.pdp.click_up.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkspaceDto {
    private String name;

    private String color;

    private Long avatarId;
}
