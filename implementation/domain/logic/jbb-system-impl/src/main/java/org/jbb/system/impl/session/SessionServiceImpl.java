package org.jbb.system.impl.session;


import org.jbb.lib.core.security.SecurityContentUser;
import org.jbb.lib.mvc.formatters.DurationFormatter;
import org.jbb.lib.mvc.formatters.LocalDateTimeFormatter;
import org.jbb.lib.mvc.repository.JbbSessionRepository;
import org.jbb.system.api.model.session.UserSession;
import org.jbb.system.api.service.SessionService;
import org.jbb.system.impl.session.model.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.session.ExpiringSession;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService{


    private final DurationFormatter durationFormatter;
    private final LocalDateTimeFormatter localDateTimeFormatter;
    private final JbbSessionRepository jbbSessionRepository;

    private final static String SESSION_CONTEXT_ATTRIBUTE_NAME = "SPRING_SECURITY_CONTEXT";

    @Autowired
    public SessionServiceImpl(JbbSessionRepository jbbSessionRepository, LocalDateTimeFormatter localDateTimeFormatter, DurationFormatter durationFormatter){
        this.jbbSessionRepository=jbbSessionRepository;
        this.durationFormatter=durationFormatter;
        this.localDateTimeFormatter=localDateTimeFormatter;
    }

    @Override
    public List<UserSession> getAllUserSessions()
    {
        Map<String, ExpiringSession> jbbSessionRepositorySessionMap = jbbSessionRepository.getSessionMap();
        return jbbSessionRepositorySessionMap.entrySet()
                .stream()
                .map(entry -> mapSessionToInternalModel(entry.getKey(),entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void terminateSession(UserSession session) {

    }

    @Override
    public void setDefaultInactiveSessionInterval(Duration maximumInactiveSessionInterval) {

    }

    @Override
    public void getDefaultInactiveSessionInterval(Duration maximumInactiveSessionInterval) {

    }

    private UserSession mapSessionToInternalModel(String sessionID, ExpiringSession expiringSession){
        return new SessionImpl().builder()
                .creationTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(expiringSession.getCreationTime()), ZoneId.systemDefault()))
                .id(sessionID)
                .inactiveTime(Duration.ofMillis(expiringSession.getMaxInactiveIntervalInSeconds()))
                .lastAccessedTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(expiringSession.getLastAccessedTime()), ZoneId.systemDefault()))
                .displayName(((SecurityContentUser) ((SecurityContextImpl) expiringSession
                        .getAttribute(SESSION_CONTEXT_ATTRIBUTE_NAME))
                        .getAuthentication().getPrincipal())
                        .getDisplayedName())
                .username(((SecurityContentUser) ((SecurityContextImpl) expiringSession
                        .getAttribute(SESSION_CONTEXT_ATTRIBUTE_NAME))
                        .getAuthentication().getPrincipal())
                        .getUsername())
                .usedTime(Duration.between(LocalDateTime.now(), LocalDateTime.ofInstant(Instant.ofEpochMilli(expiringSession.getCreationTime()), ZoneId.systemDefault())))
                .timeToLive(Duration.between(LocalDateTime.now(),(
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(expiringSession.getLastAccessedTime()), ZoneId.systemDefault())
                                .plus(expiringSession.getMaxInactiveIntervalInSeconds(), ChronoUnit.MILLIS))))
                .build();
    }

}


