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
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>

                    <meta charset="UTF-8">
                    <title>Wrong Answer</title>
                    <link rel="stylesheet" type="text/css" href="static/css/common.css">
                    <link rel="icon" href="static/favicon/favicon.ico" type="image/vnd.microsoft.icon">

                </head>
                <body>
                <h1 class="wrong_answer">Wrong answer, try again</h1>
                <a href="/task">
                    <button class="try_again">Try again</button>
                </a>

                <div class="image_container_wrong_answer">
                    <img src="static/img/wrong_answer.png">
                </div>
                </body>
                </html>""";
    }
}
