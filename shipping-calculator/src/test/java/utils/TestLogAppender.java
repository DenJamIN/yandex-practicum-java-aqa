package utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * Фиксатор работы с логами
 */
public class TestLogAppender extends AppenderBase<ILoggingEvent> {

    /**
     * Поле для фиксации логов
     */
    private final StringBuilder log = new StringBuilder();

    /**
     * Добавление строки лога
     */
    @Override
    protected void append(ILoggingEvent eventObject) {
        log.append(eventObject.getFormattedMessage()).append("\n");
    }

    /**
     * Получение логов
     */
    public String getLogs() {
        return log.toString();
    }

    /**
     * Очистка для следующего теста
     */
    public void clear() {
        log.setLength(0);
    }
}
