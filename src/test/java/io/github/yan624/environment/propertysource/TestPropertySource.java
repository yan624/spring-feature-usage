package io.github.yan624.environment.propertysource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.Map;

/**
 * @author yorz
 * @since 2023-05-16
 */
@SpringBootTest
public class TestPropertySource {

    @Autowired
    private ConfigurablePageService configurablePageService;

    @Test
    public void test() {
        Map<String, ?> mergedConfig = configurablePageService.getMergedConfig(new Locale("zh", "CN", "BOOK"));
        System.out.println(mergedConfig);
    }
}
