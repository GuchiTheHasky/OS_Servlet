package the.husky.web.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import the.husky.exception.PageGeneratorException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

public class PageGenerator {
    private static final String TEMPLATE_SOURCE_DIR = "/template";
    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public PageGenerator() {
        this.cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setClassForTemplateLoading(PageGenerator.class, TEMPLATE_SOURCE_DIR);
    }

    public static PageGenerator instance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public String getPage(String templateName) {
        return getPage(templateName, Collections.<String, Object>emptyMap());
    }

    public String getPage(String templateName, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try  {
            Template template = cfg.getTemplate(templateName);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new PageGeneratorException(String.format("Failed to generate template: %s", templateName), e);
        }
        return stream.toString();
    }
}
