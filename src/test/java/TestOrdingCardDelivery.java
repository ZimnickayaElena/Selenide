import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrdingCardDelivery {

    String generateDate (int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldDeliveryCard() {
        open ("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Самара");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME), Keys.BACK_SPACE);
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Зимницкая Елена");
        $("[data-test-id=phone] input").setValue("+79277032008");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно! Встреча успешно забронирована на " + currentDate));
    }
}

