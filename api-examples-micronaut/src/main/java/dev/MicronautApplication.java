package dev;

import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MicronautApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MicronautApplication.class);

    public static void main(String[] args) {
        LOG.info("Запуск Micronaut...");
        Micronaut.run(MicronautApplication.class, args);
    }
}
