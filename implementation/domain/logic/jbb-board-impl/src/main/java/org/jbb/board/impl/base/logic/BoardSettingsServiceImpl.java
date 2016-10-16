/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jBB Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jbb.board.impl.base.logic;

import org.apache.commons.lang.Validate;
import org.jbb.board.api.exception.BoardException;
import org.jbb.board.api.model.BoardSettings;
import org.jbb.board.api.service.BoardSettingsService;
import org.jbb.board.impl.base.data.BoardSettingsImpl;
import org.jbb.board.impl.base.properties.BoardProperties;
import org.jbb.lib.mvc.formatters.LocalDateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class BoardSettingsServiceImpl implements BoardSettingsService {
    private final BoardProperties properties;
    private final LocalDateTimeFormatter localDateTimeFormatter;
    private final Validator validator;


    @Autowired
    public BoardSettingsServiceImpl(BoardProperties properties,
                                    LocalDateTimeFormatter localDateTimeFormatter,
                                    Validator validator) {
        this.properties = properties;
        this.localDateTimeFormatter = localDateTimeFormatter;
        this.validator = validator;
    }

    @Override
    public BoardSettings getBoardSettings() {
        BoardSettingsImpl boardSettings = new BoardSettingsImpl();
        boardSettings.setBoardName(getBoardName());
        boardSettings.setDateFormat(getDateFormat());
        return boardSettings;
    }

    @Override
    public void setBoardSettings(BoardSettings boardSettings) {
        Validate.notNull(boardSettings);

        Set<ConstraintViolation<BoardSettingsImpl>> validationResult = validator.validate(new BoardSettingsImpl(boardSettings));

        if (validationResult.isEmpty()) {
            setBoardName(boardSettings.getBoardName());
            setDateFormat(boardSettings.getDateFormat());
        } else {
            throw new BoardException(validationResult);
        }
    }

    private String getBoardName() {
        return properties.boardName();
    }

    private void setBoardName(String newBoardName) {
        Validate.notEmpty(newBoardName, "Board name cannot be empty or null");
        properties.setProperty(BoardProperties.BOARD_NAME_KEY, newBoardName);
    }

    private String getDateFormat() {
        return localDateTimeFormatter.getCurrentPattern();
    }

    private void setDateFormat(String dateFormat) {
        Validate.notEmpty(dateFormat);
        localDateTimeFormatter.setPattern(dateFormat);
    }
}