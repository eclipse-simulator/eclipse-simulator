package pwr.api.entity.roll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roll
{
    int value;
    int damage;
    boolean shotFired = false;
}
