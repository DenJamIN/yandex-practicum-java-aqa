package utils;

import ch.qos.logback.classic.Logger;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

/**
 * Расширение для вложения файла с логами теста
 */
public class LoggingExtension implements BeforeEachCallback, AfterEachCallback {

    private TestLogAppender logAppender;
    private Logger logger;

    /**
     * Механизм работы перед каждым тестов.
     * Запускается фиксаторов логов по классу {@link org.example.app.ShippingCalculator}
     */
    @Override
    public void beforeEach(ExtensionContext context) {
        logAppender = new TestLogAppender();
        logAppender.start();
        logger = (Logger) LoggerFactory.getLogger("org.example.app.ShippingCalculator");
        logger.addAppender(logAppender);
    }

    /**
     * Механизм работы после каждого теста.
     * Получение логов из фиксатора и вложение к отчёту, в конце фиксатор очищается
     */
    @Override
    public void afterEach(ExtensionContext context) {
        String logs = logAppender.getLogs();
        if (!logs.isEmpty()) {
            Allure.addAttachment("Logs for " + context.getDisplayName(), "text/plain", logs, ".log");
        }
        logger.detachAppender(logAppender);
        logAppender.stop();
        logAppender.clear();
    }
}