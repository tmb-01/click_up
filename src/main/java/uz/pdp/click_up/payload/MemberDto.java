package uz.pdp.click_up.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.click_up.entity.enums.AddType;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDto {
    private Long memberId;
    private Long roleId;
    private AddType addType; // add edit remove
}
