import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.*;


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

