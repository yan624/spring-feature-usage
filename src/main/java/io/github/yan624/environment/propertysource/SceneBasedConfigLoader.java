package io.github.yan624.environment.propertysource;

import static io.github.yan624.environment.propertysource.ConfigurablePageService.withDir;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * @author yorz
 * @since 2023-05-16
 */
public class SceneBasedConfigLoader implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        MutablePropertySources mps = applicationContext.getEnvironment().getPropertySources();
        mps.addLast(loadFrom(withDir("pageConfig.yml")));
        mps.addLast(loadFrom(withDir("pageConfig_zh_CN_BOOK.yml")));
    }

    private PropertiesPropertySource loadFrom(String path) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        ClassPathResource classPathResource = new ClassPathResource(path);
        factory.setResources(classPathResource);
        // 属性源的名称为路径名，例如 configurable-page\pageConfig.yml。注意不要用 classPathResource.getPath()，它永远都用 / 作为分隔符。
        return new PropertiesPropertySource(path, factory.getObject());
    }
}
