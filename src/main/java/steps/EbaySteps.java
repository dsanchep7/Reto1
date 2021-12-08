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
