package the.husky.web.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageGeneratorTest {
    private final PageGenerator GENERATOR = new PageGenerator();

    @Test
    @DisplayName("Test, generate page to String, check content.")
    public void testGeneratePageHtmlToString() {
        String expectedPageContent = getExpectedPageContent().replaceAll("\n", "\r\n");
        String actualPageContent = GENERATOR.getPage("wrong_answer.html");

        assertEquals(expectedPageContent, actualPageContent);
    }

    private String getExpectedPageContent() {
        return "<!DOCTYPE html>" + "\n" +
                "<html lang=\"en\">" + "\n" +
                "<head>" + "\n\n" +

                "<meta charset=\"UTF-8\">" + "\n" +
                "<title>Wrong Answer</title>" + "\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"static/css/common.css\">" + "\n\n" +

                "</head>" + "\n" +
                "<body>" + "\n" +
                "<h1 class=\"wrong_answer\">Wrong answer, try again</h1>" + "\n" +
                "<a href=\"/task\">" + "\n" +
                "<button class=\"try_again\">Try again</button></a>" + "\n\n" +

                "<div class=\"image_container_wrong_answer\">" + "\n" +
                "<img src=\"static/img/wrong_answer.png\">" + "\n" +
                "</div>" + "\n" +
                "</body>" + "\n" +
                "</html>";
    }
}
