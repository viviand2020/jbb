package org.jbb.system.api.database.h2;

import java.util.Optional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.valuehandling.UnwrapValidatedValue;
import org.jbb.system.api.database.DatabaseProvider;
import org.jbb.system.api.database.DatabaseProviderSettings;

@Getter
@Setter
@Builder
public class H2ManagedServerSettings implements DatabaseProviderSettings {

    @NotBlank
    private String databaseFileName;

    @Min(1)
    private int port;

    @NotBlank
    private String username;

    @NotBlank
    private String usernamePassword;

    @NotBlank
    private String filePassword;

    @NotNull
    private H2ConnectionType connectionType;

    @NotNull
    @UnwrapValidatedValue
    private Optional<H2EncryptionAlgorithm> encryptionAlgorithm;

    @Override
    public DatabaseProvider getDatabaseProvider() {
        return DatabaseProvider.H2_MANAGED_SERVER;
    }
}