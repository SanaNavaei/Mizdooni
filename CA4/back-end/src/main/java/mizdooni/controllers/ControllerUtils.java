package mizdooni.controllers;

import java.util.Map;

public class ControllerUtils {
    static final String PARAMS_MISSING = "parameters missing";
    static final String PARAMS_BAD_TYPE = "bad parameter type";

    static boolean containsKeys(Map<String, ?> params, String... keys) {
        for (String key : keys) {
            if (!params.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    static boolean doExist(String... values) {
        for (String value : values) {
            if (value == null || value.isBlank()) {
                return false;
            }
        }
        return true;
    }
}
