package ru.qatools.school.steps.websteps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.qatools.school.pages.MainPage;
import ru.qatools.school.pages.MainPageMethods;
import ru.yandex.qatools.allure.annotations.Step;
import java.util.List;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.isDisplayed;

/**
 * Created by kurau.
 */
public class DefaultSteps {

    public static final String MAIN_PAGE = "http://weather.lanwen.ru/#?cities=%s";
    private static final String NEW_WIDGET = "What a city?";

    private WebDriver driver;

    public DefaultSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открываем главную страницу для города «{0}»")
    public void openMainPageWithCity(String city) {
        driver.get(format(MAIN_PAGE, city));
    }

    @Step("Должны видеть на странице «{0}»")
    public void shouldSee(WebElement element) {
        assertThat("Должны видеть элемент", element, isDisplayed());
    }

    @Step("Заголовок виджета должен совпадать с запрошенным городом")
    public void shouldSeeTitleWidgetEqualCity(String city) {
        assertThat("Заголовок города равен названию виджета",
                onMainPage().getPlaces().get(0).getText(), equalTo(city));
    }

    private void addWidgetOnMainPage(String city) {
        mainPageMethods().addWidget();
        mainPageMethods().renameWidget(NEW_WIDGET, city);
    }

    @Step("На главной странице виджеты добавляются")
    public void shouldSeeWidgetAdd(String city) {
        addWidgetOnMainPage(city);
        assertThat("Widget has", city, is(mainPageMethods().findWidget(city).getText()));
    }

    @Step("На главной странице виджет можно удалить")
    public void shouldSeeWidgetRemove(String removeCity) {
        int count = mainPageMethods().countWidgets();
        mainPageMethods().clickOnElement(mainPageMethods().findWidget(removeCity));
        assertThat("Количество виджетов не уменьшилось на единицу после удаления одного виджета", mainPageMethods().countWidgets(), is(count - 1));
    }

    @Step("На ввод не полного названия города должно срабатывать автозаполнение")
    public void shouldAutocompliteCity(String city) {
        mainPageMethods().renameWidget(mainPageMethods().allWidgets().get(0).getPlace().getText(), city.substring(0, city.length()/2));
        WebElement element = (new WebDriverWait(driver, 10))
                .until((ExpectedCondition<WebElement>) d -> {
                    List<WebElement> elements = driver.findElements(By.cssSelector(".city__name"));
                    for (WebElement element1 : elements) {
                        if (element1.getText().equals(city)) {
                            return element1;
                        }
                    }
                    return null;
                });
        element.click();
        assertThat("Заголовок города равен названию набираемого города",
                driver.findElement(By.cssSelector(".inplace.inplace_displayed")).getText(), equalTo(city));
    }

    @Step("Должны меняться форматы вывода градуса")
    public void shouldSeeChangeFormatDegree() {
        driver.findElement(By.cssSelector(".weather-temperature.md-12")).click();
        assertThat("Не произошло преобразование градусов цельсия в градусы кельвина", driver.findElement(By.cssSelector(".weather-temperature__unit")).getText(), equalTo("°K"));
        driver.findElement(By.cssSelector(".weather-temperature.md-12")).click();
        assertThat("Не произошло преобразование градусов в кельвинах в градусы фаренгейта", driver.findElement(By.cssSelector(".weather-temperature__unit")).getText(), equalTo("°F"));
        driver.findElement(By.cssSelector(".weather-temperature.md-12")).click();
        assertThat("Не произошло преобразование градусов фаренгейта в градусы °Kaif", driver.findElement(By.cssSelector(".weather-temperature__unit")).getText(), equalTo("°Kaif"));
    }

    private MainPage onMainPage() {
        return new MainPage(driver);
    }

    private MainPageMethods mainPageMethods() {
        return new MainPageMethods(driver);
    }
}
