/*
 * Copyright (C) 2017 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.lib.properties.encrypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

import static org.jbb.lib.properties.encrypt.EncryptionPlaceholderUtils.extractFromDecPlaceholder;
import static org.jbb.lib.properties.encrypt.EncryptionPlaceholderUtils.extractFromEncPlaceholder;
import static org.jbb.lib.properties.encrypt.EncryptionPlaceholderUtils.isInDecPlaceholder;
import static org.jbb.lib.properties.encrypt.EncryptionPlaceholderUtils.isInEncPlaceholder;
import static org.jbb.lib.properties.encrypt.EncryptionPlaceholderUtils.surroundWithDecPlaceholder;

@Component
@RequiredArgsConstructor
public class PropertiesEncryption {
    private final StandardPBEStringEncryptor encryptor;
    private final PswdValueResolver pswdValueResolver;

    private boolean encryptionEnabled;

    @PostConstruct
    final void reconfigureEncryption() {
        pswdValueResolver.resolvePassword();
        Optional<String> password = pswdValueResolver.getPassword();
        if (password.isPresent()) {
            this.encryptor.setPassword(password.get());
            encryptionEnabled = true;
        } else {
            encryptionEnabled = false;
        }
    }

    public String encryptIfNeeded(String value) {
        if (isInEncPlaceholder(value)) {
            if (encryptionEnabled) {
                return surroundWithDecPlaceholder(encryptor.encrypt(extractFromEncPlaceholder(value)));
            } else {
                return extractFromEncPlaceholder(value);
            }
        } else {
            return value;
        }
    }

    public String decryptIfNeeded(String value) {
        if (isInDecPlaceholder(value)) {
            if (encryptionEnabled) {
                return encryptor.decrypt(extractFromDecPlaceholder(value));
            } else {
                throw new IllegalStateException("Can't decrypt '" + value + "' because properties encryption is disabled");
            }
        } else {
            return value;
        }
    }


    boolean isEncryptionEnabled() {
        return encryptionEnabled;
    }
}
