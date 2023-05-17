package io.github.yan624.environment.propertysource;

/**
 * @author yorz
 * @since 2023-05-16
 */
public enum PageScene {
    BOOK,
    AUDIO,
    VIDEO,
    ;

    public static boolean isValid(String code) {
        try {
            PageScene.valueOf(code);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
