package the.husky.web.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

public class PageGenerator {
    private static final String TEMPLATE = "src\\main\\resources\\template";
    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public PageGenerator() {
        this.cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    }

    public static PageGenerator instance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public String getPage(String fileName) {
        return getPage(fileName, Collections.<String, Object>emptyMap());
    }

    public String getPage(String fileName, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(TEMPLATE + File.separator + fileName);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(">>>" + e.getMessage() + " \r\n" + e.getCause() + ">>>");
        }
        return stream.toString();
    }
}
