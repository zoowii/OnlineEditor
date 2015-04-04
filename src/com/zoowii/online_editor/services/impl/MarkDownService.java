package com.zoowii.online_editor.services.impl;

import com.zoowii.online_editor.services.IMarkDownService;
import com.zoowii.playmore.annotation.Service;
import org.markdown4j.Markdown4jProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by zoowii on 15/4/4.
 */
@Service
public class MarkDownService implements IMarkDownService {
    private static final Logger LOG = LoggerFactory.getLogger(MarkDownService.class);
    private static final Markdown4jProcessor markdownProcessor = new Markdown4jProcessor();
    @Override
    public String renderMarkDown(String markDownText) {
        try {
            return markdownProcessor.process(markDownText);
        } catch (IOException e) {
            LOG.error("markdown process error", e);
            return null;
        }
    }
}
