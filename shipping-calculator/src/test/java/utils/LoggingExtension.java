package utils;

import ch.qos.logback.classic.Logger;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

public class LoggingExtension implements BeforeEachCallback, AfterEachCallback {

    private TestLogAppender logAppender;
    private Logger targetLogger;

    @Override
    public void beforeEach(ExtensionContext context) {
        logAppender = new TestLogAppender();
        logAppender.start();
        targetLogger = (Logger) LoggerFactory.getLogger("org.example.app.ShippingCalculator");
        targetLogger.addAppender(logAppender);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String logs = logAppender.getLog();
        if (!logs.isEmpty()) {
            Allure.addAttachment("Logs for " + context.getDisplayName(), "text/plain", logs, ".log");
        }
        targetLogger.detachAppender(logAppender);
        logAppender.stop();
        logAppender.clear();
    }
}