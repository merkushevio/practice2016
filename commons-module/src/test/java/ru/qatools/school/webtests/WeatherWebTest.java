package ru.qatools.school.webtests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import ru.qatools.school.pages.MainPage;
import ru.qatools.school.rules.WebDriverRule;
import ru.qatools.school.steps.websteps.DefaultSteps;
import ru.qatools.school.tp.TPInformerRule;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import ru.yandex.qatools.allure.annotations.Title;


public class WeatherWebTest {

    private static final String MOSCOW = "Moscow";
    private static final String SPB = "Saint Petersburg";

    private DefaultSteps defaultSteps;

    @Rule
    public WebDriverRule webDriverRule = new WebDriverRule();
    @Rule
    public TPInformerRule tms = new TPInformerRule("merkushevio");


    @Before
    public void initSteps() {
        defaultSteps = new DefaultSteps(webDriverRule.getDriver());
    }

    private MainPage onMainPage() {
        return new MainPage(webDriverRule.getDriver());
    }

    @Test
    @TestCaseId("1")
    @Title("Должны видеть виджет на главной странице")
    public void shouldSeeWidgetOnMainPage() {
        defaultSteps.openMainPageWithCity(MOSCOW);
        defaultSteps.shouldSee(onMainPage().getWeatherWidgets().get(0));
        defaultSteps.shouldSeeTitleWidgetEqualCity(MOSCOW);
        defaultSteps.shouldSeeWidgetElements();
    }

    @Test
    @Title("Должны увидеть главную страницу только с кнопокой добавить виджет")
    @TestCaseId("2")
    public void shouldSeeButtonOnMainPage() {
        defaultSteps.openMainPageWithCity("");
        defaultSteps.shouldSeeButtonAddWidgetOnMainPage();
        defaultSteps.shouldSeeOnlyButtonAddWidget();
    }

    @Test
    @Title("Должны увидеть главную страницу только с кнопкой добавить виджет")
    @TestCaseId("3")
    public void shouldSeeButtonAddWidgetOnMainPageWithoutParameter() {
        defaultSteps.shouldSeeButtonOnlyAddWidgetOnMainPageWithoutParameter();
        defaultSteps.shouldSeeOnlyButtonAddWidget();
    }

    @Test
    @Title("Можем добавлять виджет на главной странице")
    @TestCaseId("4")
    public void shouldSeeAddWidgetOnMainPage() {
        defaultSteps.openMainPageWithCity(MOSCOW);
        defaultSteps.shouldSeeWidgetAdd();
    }

    @Test
    @Title("Можем удалить второй виджет на главной странице")
    @TestCaseId("9")
    public void shouldSeeRemoveSecondWidgetOnMainPage() {
        defaultSteps.openMainPageWithCity(SPB);
        defaultSteps.shouldSeeWidgetAdd();
        defaultSteps.shouldSeeWidgetRemove();
    }

    @Test
    @Title("Может удалить последний виджет на главной странице")
    @TestCaseId("10")
    public void shouldSeeRemoveLastWidgetOnMainPage() {
        defaultSteps.openMainPageWithCity(SPB);
        defaultSteps.shouldSeeWidgetRemove();
    }

    @Test
    @Title("Должны увидеть автозаполнение при не полном наборе названия")
    @TestCaseId("12")
    public void shouldAutoCompleteCity() {
        defaultSteps.openMainPageWithCity(MOSCOW);
        defaultSteps.shouldAutocompliteCity(SPB);
    }

    @Test
    @Title("Можем переименовать виджет")
    @TestCaseId("11")
    public void shouldSeeRenameWidget() {
        defaultSteps.openMainPageWithCity(MOSCOW);
        defaultSteps.shouldSeeRenameWidget(MOSCOW, SPB);
    }

    @Test
    @Title("Можем очистить название виджета, после чего назначить новое имя")
    @TestCaseId("13")
    public void shouldSeeClearWidgetName() {
        defaultSteps.openMainPageWithCity(SPB);
        defaultSteps.shouldSeeRenameWidget(SPB, "");
        defaultSteps.shouldSeeRenameWidget("", SPB);
    }

    @Test
    @Title("Можем написать не полное имя виджета")
    @TestCaseId("14")
    public void shouldSeeNotFullNameWidget() {
        defaultSteps.openMainPageWithCity(MOSCOW);
        defaultSteps.shouldSeeRenameWidget(MOSCOW, SPB.substring(0, SPB.length() / 3));
    }

    @Test
    @Title("Можем менять форматы вывода градусов")
    @ru.yandex.qatools.allure.annotations.TestCaseId("15")
    public void shouldSeeChangeFormatDegree() {
        defaultSteps.openMainPageWithCity(SPB);
        defaultSteps.shouldSeeChangeFormatTemperature();
    }
}



