Feature: HU-001 Buscador Ebay
  Yo como usuario de Ebay
  Quiero buscar un producto en la plataforma
  Para ver el nombre del producto en pantalla

  Scenario: Buscar producto
    Given que me encuentro en la pagina de Ebay
    When busque el producto
    Then podre ver en pantalla
