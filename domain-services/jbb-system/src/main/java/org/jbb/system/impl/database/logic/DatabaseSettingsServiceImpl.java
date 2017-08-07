package org.jbb.system.impl.database.logic;

import java.beans.PropertyChangeEvent;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.jbb.lib.db.DbProperties;
import org.jbb.lib.db.DbPropertyChangeListener;
import org.jbb.system.api.database.DatabaseConfigException;
import org.jbb.system.api.database.DatabaseSettings;
import org.jbb.system.api.database.DatabaseSettingsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseSettingsServiceImpl implements DatabaseSettingsService {
    private final DbProperties dbProperties;
    private final DatabaseSettingsFactory databaseSettingsFactory;
    private final Validator validator;
    private final ReconnectionToDbPropertyListener reconnectionPropertyListener;
    private final DbPropertyChangeListener dbPropertyChangeListener;
    private final ConnectionToDatabaseEventSender eventSender;
    private final DatabaseSettingsSaver settingsSaver;

    @PostConstruct
    public void addReconnectionPropertyListenerToDbProperties() {
        dbProperties.addPropertyChangeListener(reconnectionPropertyListener);
    }

    @Override
    public DatabaseSettings getDatabaseSettings() {
        return databaseSettingsFactory.currentDatabaseSettings();
    }

    @Override
    public void setDatabaseSettings(DatabaseSettings newDatabaseSettings) {
        Validate.notNull(newDatabaseSettings);

        Set<ConstraintViolation<DatabaseSettings>> validationResult = validator.validate(newDatabaseSettings);
        if (!validationResult.isEmpty()) {
            throw new DatabaseConfigException(validationResult);
        }

        dbProperties.removePropertyChangeListener(dbPropertyChangeListener);
        dbProperties.removePropertyChangeListener(reconnectionPropertyListener);
        try {
            settingsSaver.setDatabaseSettings(newDatabaseSettings);
        } finally {
            dbProperties.addPropertyChangeListener(dbPropertyChangeListener);
            addReconnectionPropertyListenerToDbProperties();
        }
        dbPropertyChangeListener.propertyChange(new PropertyChangeEvent(this, "db", null, null));
        eventSender.emitDatabaseSettingsChangedEvent();
    }
}
