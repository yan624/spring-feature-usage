package io.github.yan624.environment.propertysource;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @author yorz
 * @since 2023-05-16
 */
@Service
public class ConfigurablePageServiceImpl implements ConfigurablePageService, EnvironmentAware {

    private PropertySources propertySources;

    @Override
    public void setEnvironment(Environment environment) {
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        this.propertySources = env.getPropertySources();
    }

    protected CompositePropertySource getLocalSceneBasedPropertySource(Locale locale) {
        // todo: 缓存
        CompositePropertySource cps = new CompositePropertySource("local merged config");

        // 第一优先级：具体语言的场景化配置
        String sceneNameLocale = resolveSourceKey(locale);
        PropertySource<?> sceneSourceLocale = propertySources.get(sceneNameLocale);
        if (sceneSourceLocale != null) {
            cps.addPropertySource(sceneSourceLocale);
        }

        // 第二优先级：中文的场景化配置
        if (!("zh".equals(locale.getLanguage()) && "CN".equals(locale.getCountry()))) {
            String sceneNameZh = resolveSourceKey(locale);
            PropertySource<?> sceneSourceZh = propertySources.get(sceneNameZh);
            if (sceneSourceZh != null) {
                cps.addPropertySource(sceneSourceZh);
            }
        }

        // 最低优先级：默认配置
        // 第二优先级：中文的场景化配置
        String defaultName = resolveSourceKey(null);
        PropertySource<?> defaultSource = propertySources.get(defaultName);
        if (defaultSource != null) {
            cps.addPropertySource(defaultSource);
        }
        return cps;
    }

    private String resolveSourceKey(Locale locale) {
        return ConfigurablePageService.buildPath(locale);
    }

    @Override
    public Map<String, ?> getMergedConfig(Locale locale) {
        if (!PageScene.isValid(locale.getVariant())) {
            return new HashMap<>();
        }
        CompositePropertySource cps = getLocalSceneBasedPropertySource(locale);
        Properties properties = mergeConfigItemFrom(cps);
        return hierarchyPropertyToJson(properties);
    }

    private Properties mergeConfigItemFrom(CompositePropertySource cps) {
        Properties properties = new Properties();
        for (String propertyName : cps.getPropertyNames()) {
            properties.put(propertyName, cps.getProperty(propertyName));
        }
        return properties;
    }

    private Map<String, ?> hierarchyPropertyToJson(Properties properties) {
        return (Map) properties;
    }
}
