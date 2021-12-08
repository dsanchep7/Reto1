package stepsDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import drivers.GoogleChromeDriver;
import steps.EbaySteps;

import java.io.IOException;

public class EbayBuscadorStepDefinitions {

    EbaySteps ebaySteps = new EbaySteps();

    @Given("^que me encuentro en la pagina de Ebay$")
    public void queMeEncuentroEnLaPaginaDeEbay() {
        // Write code here that turns the phrase above into concrete actions
        ebaySteps.abrirPagina();
    }


    @When("^busque el producto$")
    public void busqueElProducto() throws IOException, InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        ebaySteps.ValidacionExcel();
    }

    @Then("^podre ver en pantalla$")
    public void podreVerEnPantalla() {
        // Write code here that turns the phrase above into concrete actions
        GoogleChromeDriver.driver.quit();
    }

}
