package org.jbb.lib.properties;

import org.aeonbits.owner.ConfigFactory;

public final class ModuleConfigFactory {
    private final static SystemProperties SYSTEM_PROPERTIES = ConfigFactory.create(
            SystemProperties.class, System.getProperties(), System.getenv());

    private ModuleConfigFactory() {
        // util class...
    }

    public static <T extends ModuleConfig> T create(Class<? extends T> clazz) {
        return ConfigFactory.create(clazz);
        // TODO add PropertyChangeListener for logging
    }

    public static SystemProperties systemProperties() {
        return SYSTEM_PROPERTIES;
    }
}
