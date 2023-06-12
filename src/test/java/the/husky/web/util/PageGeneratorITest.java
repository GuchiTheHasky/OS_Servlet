package the.husky.web.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageGeneratorITest {
    private final PageGenerator GENERATOR = new PageGenerator();

    @Test
    public void teat() {
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
                </head>
                <body>
                <h1>Wrong answer, try again</h1>
                <a href="/task">
                    <button>Try again</button></a>
                
                </body>
                </html>""";
    }
}
