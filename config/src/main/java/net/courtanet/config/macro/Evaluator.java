/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package net.courtanet.config.macro;

import java.util.Map;

public interface Evaluator {
    String eval(Map<String, Object> conf, String key, String defaultReplacement, MacroParamProcessor paramProcessor,
                    boolean encodeHTML);
}
