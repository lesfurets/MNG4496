/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package net.courtanet.config.macro;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class MacroProcessorTest {
    private static final String UNEXPECTED = "Unexpected";

    @Test
    public void testCss() throws IOException, PropertyParsingException {
        final Properties props = new Properties();
        props.load(MacroProcessorTest.class.getResourceAsStream("default.properties"));
        final String wizzard_css = IOUtils.toString(MacroProcessorTest.class.getResourceAsStream("wizard.css"));
        final Map<String, String> conf = new HashMap<String, String>();
        for (Entry<Object, Object> entry : props.entrySet())
            conf.put(entry.getKey().toString(), entry.getValue().toString());
        final String expanded_css = MacroProcessor.replaceProperties(wizzard_css, conf, 2);
        assertFalse(expanded_css.indexOf("${") >= 0);
    }

    @Test
    public void testRecurcive_plus_default_value() throws IOException, PropertyParsingException {
        final Properties props = new Properties();
        props.load(MacroProcessorTest.class.getResourceAsStream("recursive_default.properties"));
        final String recursive_template = IOUtils.toString(MacroProcessorTest.class
                        .getResourceAsStream("recursive_default.template"));
        final Map<String, String> conf = new HashMap<String, String>();
        for (Entry<Object, Object> entry : props.entrySet())
            conf.put(entry.getKey().toString(), entry.getValue().toString());
        final String expanded_recursive_template = MacroProcessor.replaceProperties(recursive_template, conf,
                        UNEXPECTED);
        assertNotNull(expanded_recursive_template);
        assertTrue(expanded_recursive_template.contains(UNEXPECTED));
    }
}
