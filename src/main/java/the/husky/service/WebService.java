package the.husky.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import the.husky.security.SecurityService;
import the.husky.service.cache.CacheService;

@Getter
@NoArgsConstructor
public class WebService {
    private SecurityService securityService;
    private CacheService cacheService;

    public WebService(SecurityService securityService, CacheService cacheService) {
        this.securityService = securityService;
        this.cacheService = cacheService;
    }
}
