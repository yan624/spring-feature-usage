package io.github.yan624.environment.propertysource;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Locale;
import java.util.Map;

/**
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
