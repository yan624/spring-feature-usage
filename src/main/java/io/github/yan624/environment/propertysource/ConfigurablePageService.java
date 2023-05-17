package io.github.yan624.environment.propertysource;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Locale;
import java.util.Map;

/**
 * <p>可配置化页面服务</p>
 * <p>提供场景化配置。当不同场景共用同一个页面时，前端可以根据场景码获得场景化配置。这样对于不同的场景可以在页面上显示不同的文案或效果。</p>
 * @author yorz
 * @since 2023-05-16
 */
public interface ConfigurablePageService {

    /**
     * 场景化配置文件
     */
    String CONFIG_FILE_DIR = "configurable-page";
    /**
     * 场景化配置文件名称
     */
    String CONFIG_FILE_NAME = "pageConfig";
    String CONTROL_CONFIG_KEY = "control";
    String TEXT_CONFIG_KEY = "text";

    static String withDir(String filename) {
        return CONFIG_FILE_DIR + File.separator + filename;
    }

    static String buildPath(@Nullable Locale locale) {
        String partPath = withDir(CONFIG_FILE_NAME);
        if (locale != null) {
            partPath += "_" + locale.getLanguage() + "_" + locale.getCountry() + "_" + locale.getVariant();
        }
        return partPath + ".yml";
    }

    Map<String, ?> getMergedConfig(Locale locale);
}
