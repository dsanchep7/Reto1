# Mi primer reto

Mi primer reto consta de realizar la automatización de ingreso y busqueda de productos en la paginan https://co.ebay.com/

## Empezando

Las pruebas fueron apoyados por el IDE Intelling con el gestor de dependencias gradle sobre la parte front de la pagina apoyado por el patron de diseño POM y la herramienta de software gherkin.


### Implementación de POM
 
Se realiza en los paquetes 
- drivers Clase - ChromeDriver )
- pages (Clase - Pages)
- steps (Clase - Steps)

####   La clase ChromeDriver
  ```
Esta clase se utiliza para levantar el navegador

package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleChromeDriver {

    public static WebDriver driver;

    public static void chromeWebDriver(String url){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-infobars");
        driver = new ChromeDriver(options);
        driver.get(url);
    }

}

  ```
####  La clase pages
  ```
Es donde definimos los xpath para la iteración con la pagina que estamos automatizando.

package pages;

import org.openqa.selenium.By;

public class EbayPage {

    By txtBuscador = By.xpath("//input[@placeholder='Buscar artículos']");
    By btnBuscador = By.xpath("//input[@class ='btn btn-prim gh-spr' ]");
    By btnElementoBusqueda;
    By txtElementoBusqueda;

    public By getTxtBuscador() {
        return txtBuscador;
    }

    public By getBtnBuscador() {
        return btnBuscador;
    }

    public By getBtnElementoBusqueda() {
        return btnElementoBusqueda;
    }

    public By getTxtElementoBusqueda() {
        return txtElementoBusqueda;
    }

    public void setBtnElementoBusqueda(String producto) {
        //this.btnElementoBusqueda = By.xpath("//li[@class='s-item s-item__pl-on-bottom' and @data-view='"+producto+"')]");
        this.btnElementoBusqueda = By.xpath("//li[@class='s-item s-item__pl-on-bottom']//div[@class='s-item__wrapper clearfix']//h3[contains(text(),'"+producto+"')]");
    }
    //li[@class='s-item s-item__pl-on-bottom']//a[@class='s-item__link']//h3[contains(text()

    public void setTxtElementoBusqueda(String producto) {
        this.txtElementoBusqueda = By.xpath("//h3[contains(text(),'"+producto+"')]");
    }
}
  ```

####  La clase steps
  ```
Es donde definimos el paso a paso de las acciones que vamos a realizar en el código a través de la creación de metodos para la correcta funcionalidad de la automatización

package steps;

import drivers.GoogleChromeDriver;
import excel.LeerExcel;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.EbayPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class EbaySteps {

       LeerExcel leerexcel = new LeerExcel();
       EbayPage ebayPage = new EbayPage();

        public void abrirPagina(){
            GoogleChromeDriver.chromeWebDriver("https://co.ebay.com/");
        }

        public void buscarElementoEnEbay(String producto){
            GoogleChromeDriver.driver.findElement(ebayPage.getTxtBuscador()).sendKeys(producto);
            GoogleChromeDriver.driver.findElement(ebayPage.getBtnBuscador()).click();
            ebayPage.setBtnElementoBusqueda(producto);
            GoogleChromeDriver.driver.findElement(ebayPage.getBtnElementoBusqueda()).click();
        }

        public void buscarElementoEnEbays(String productos){
            try {
                escribirEnTexto(ebayPage.getTxtBuscador(), productos);
                Thread.sleep(1000);
                clicEnElemento(ebayPage.getBtnBuscador());
                Thread.sleep(1000);
                ebayPage.setBtnElementoBusqueda(productos);
                Thread.sleep(1000);
                clicEnElemento(ebayPage.getBtnElementoBusqueda());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public void validarElementoEnPantalla(String producto){
            ebayPage.setTxtElementoBusqueda(producto);
            Assert.assertEquals(producto.replace("  "," "),GoogleChromeDriver.driver.findElement(ebayPage.getTxtElementoBusqueda()).getText());
        }

        public void escribirEnTexto(By elemento, String texto) {
            GoogleChromeDriver.driver.findElement(elemento).clear();
            GoogleChromeDriver.driver.findElement(elemento).sendKeys(texto);
        }

        public void clicEnElemento(By elemento) {
            GoogleChromeDriver.driver.findElement(elemento).click();
        }

        public void ValidacionExcel () throws IOException, InterruptedException {

        ArrayList<Map<String, String>> listaProductos;
        listaProductos = leerexcel.leerDatosDeHojaDeExcel("productos.xlsx","Hoja1");
        for (Map<String, String> datos: listaProductos){
            String producto = datos.get("Producto");
            buscarElementoEnEbays(producto);
            validarElementoEnPantalla(producto);
            cambiarPestañaInicio();
        }
      }

      public void cambiarPestañaInicio() throws InterruptedException {
          //psdbComponent.clickDocumentLink();
          Thread.sleep(2000);
          ArrayList<String> tabs2 = new ArrayList<String> (GoogleChromeDriver.driver.getWindowHandles());
          GoogleChromeDriver.driver.switchTo().window(tabs2.get(1));
          GoogleChromeDriver.driver.close();
          GoogleChromeDriver.driver.switchTo().window(tabs2.get(0));
        }
    }

  ```


### Implementación del Cucumber

Se realiza en los paquetes 

-runners (Clase - Runners )
-stepsDefinition (Clase - stepsDefinition)
-Directorio feature (Archivo.feature)


####  EL runner
  ```
Es el que nos permite ejecutar el proyecto.


package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src\\test\\resources\\features\\EbayBuscador.feature",
        glue = "stepsDefinitions",
        snippets = SnippetType.CAMELCASE
)

public class EbayBuscadorRunner {

}
  ```

#### StepsDefinition
  ```
Aqui se definen los paso a paso que se van hacer en la pagina cuando se ejecute la automatización que se componen por  el given, when y then.


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

  ```

#### Los feature
  ```
Es donde se arma la historia de usuario, la cual tiene una estructura inicial y final.

Feature: HU-001 Buscador Ebay
  Yo como usuario de Ebay
  Quiero buscar un producto en la plataforma
  Para ver el nombre del producto en pantalla

  Scenario: Buscar producto
    Given que me encuentro en la pagina de Ebay
    When busque el producto
    Then podre ver en pantalla
 ```
