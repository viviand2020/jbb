package org.jbb.system.impl.cache.logic.provider;

import org.jbb.lib.cache.JbbCacheManager;
import org.jbb.system.api.cache.CacheProvider;
import org.jbb.system.api.cache.CacheSettings;
import org.jbb.system.api.cache.CaffeineSettings;
import org.springframework.stereotype.Component;

@Component
public class CaffeineProviderManager implements CacheProviderManager<CaffeineSettings> {

    public static final String PROVIDER_PROPERTY_VALUE = JbbCacheManager.CAFFEINE_PROVIDER_NAME;

    @Override
    public CacheProvider getProviderName() {
        return CacheProvider.CAFFEINE_EMBEDDED;
    }

    @Override
    public CaffeineSettings getCurrentProviderSettings() {
        return new CaffeineSettings();
    }

    @Override
    public void setProviderSettings(CacheSettings newCacheSettings) {
        // no action needed...
    }
}
