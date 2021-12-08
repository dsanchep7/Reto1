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

