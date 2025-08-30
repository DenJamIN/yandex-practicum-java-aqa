package utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class TestLogAppender extends AppenderBase<ILoggingEvent> {
    private final StringBuilder log = new StringBuilder();

    @Override
    protected void append(ILoggingEvent eventObject) {
        log.append(eventObject.getFormattedMessage()).append("\n");
    }

    public String getLog() {
        return log.toString();
    }

    public void clear() {
        log.setLength(0);
    }
}
