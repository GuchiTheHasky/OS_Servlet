package the.husky.service;

import lombok.Data;
import the.husky.security.SecurityService;
import the.husky.service.cache.CacheService;

@Data
public class WebService {
    private SecurityService securityService;
    private CacheService cacheService;

    public WebService(SecurityService securityService, CacheService cacheService) {
        this.securityService = securityService;
        this.cacheService = cacheService;
    }
}
