/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package net.courtanet.config.macro;

import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

public class DefaultEvaluator implements Evaluator {
    private static final Logger LOG = Logger.getLogger(DefaultEvaluator.class);

    @Override
    public String eval(Map<String, Object> conf, String key, String replacement, MacroParamProcessor paramProcessor,
                    boolean encodeHTML) {
        if (conf == null) {
            return replacement;
        }
        Object value = conf.get(key);
        if (value == null) {
            LOG.info(key + " is unknown in configuration");
            // If the key is unknown we try to call a MacroParamProcessor to process it.
            if (paramProcessor != null) {
                value = paramProcessor.processParam(key);
                if (value != null) {
                    conf.put(key, value);
                }
            }
        }
        if (value == null && replacement != null && encodeHTML) {
            return escapeHtml(replacement);
        } else if (value == null && replacement != null) {
            return replacement;
        } else if (value == null) {
            return MacroProcessor.REF_PREFIX + key + MacroProcessor.REF_SUFFIX;
        } else if (encodeHTML) {
            return escapeHtml(value);
        }
        return value.toString();
    }

    private static final String escapeHtml(Object value) {
        return StringEscapeUtils.escapeHtml4(value.toString()).replace("\r\n", "<br>").replace("\n", "<br>");
    }
}
