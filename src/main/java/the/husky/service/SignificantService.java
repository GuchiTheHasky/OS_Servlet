package the.husky.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@NoArgsConstructor
public class SignificantService {
    private UserService userService;
    private VehicleService vehicleService;

    @Autowired
    public SignificantService(UserService userService, VehicleService vehicleService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
    }

}
