package com.example;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
// Historia de Usuario: 
// Como estudiante interesado en una beca, quiero ver los requisitos de la Beca Bachiller.
//
// Caso de Prueba: TC-202 (actualizado)
// Título: Verificar que la página de requisitos de Beca Bachiller muestre exactamente los 3 requisitos
//
// PASO 1. Ingresar a https://lpz.ucb.edu.bo/
// PASO 2. Hacer clic en el botón "Becas" 
// PASO 3. Hacer clic en el botón "Requisitos" 
//
// Resultado Esperado: 
// La página debe mostrar los siguientes tres requisitos:
// 1. "Ser boliviana (o)."
// 2. "Estar cursando el sexto de secundaria en la gestión 2026."
// 3. "Haber obtenido un buen rendimiento académico, con un promedio igual o mayor a 80/100 en las libretas de: 3ro, 4to, 5to y el boletín del primer trimestre de 6to de secundaria."
/****************************************/

// Ejecución: mvn clean compile test -Dtest=BecaBachillerRequisitosTest

public class BecaBachillerRequisitosTest {

    private WebDriver driver;

    @BeforeTest
    public void setDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Intentar leer ruta del binario de Brave desde propiedad o variable de entorno
        String bravePath = System.getProperty("brave.binary");
        if (bravePath == null || bravePath.isEmpty()) {
            bravePath = System.getenv("BRAVE_BINARY");
        }
        if (bravePath == null || bravePath.isEmpty()) {
            // Ruta por defecto en Windows; el usuario puede personalizar pasando -Dbrave.binary=... o estableciendo BRAVE_BINARY
            bravePath = "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe";
        }
        System.out.println("Usando Brave binary: " + bravePath);
        options.setBinary(bravePath);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void verificarRequisitosBecaBachiller() {
        
        // PASO 1: Ir a la página principal
        driver.get("https://lpz.ucb.edu.bo/");
        esperar(3);
        
        // PASO 2: Hacer clic en "Becas"
        WebElement becasLink = driver.findElement(By.xpath("//*[@id=\"menu-item-207841\"]/a"));
        System.out.println("Texto del enlace Becas: " + becasLink.getText());
        becasLink.click();
        esperar(4); // Esperar a que cargue la página de becas
        
        // PASO 3: Hacer clic en "Requisitos"
        WebElement requisitosBoton = driver.findElement(By.xpath("//*[@id=\"post-207960\"]/div/div/div/div[2]/div[4]/div[2]/div[2]/a"));
        System.out.println("Botón Requisitos encontrado: " + requisitosBoton.getText());
        requisitosBoton.click();
        esperar(5); // Esperar a que cargue la página de requisitos
        
        // VERIFICACIÓN: Buscar el texto completo de cada requisito en el cuerpo de la página
        String bodyText = driver.findElement(By.tagName("body")).getText();
        
        String requisito1 = "Ser boliviana (o).";
        String requisito2 = "Estar cursando el sexto de secundaria en la gestión 2026.";
        String requisito3 = "Haber obtenido un buen rendimiento académico, con un promedio igual o mayor a 80/100 en las libretas de: 3ro, 4to, 5to y el boletín del primer trimestre de 6to de secundaria.";
        
        boolean tieneReq1 = bodyText.contains(requisito1);
        boolean tieneReq2 = bodyText.contains(requisito2);
        boolean tieneReq3 = bodyText.contains(requisito3);
        
        System.out.println("¿Contiene requisito 1? " + tieneReq1);
        System.out.println("¿Contiene requisito 2? " + tieneReq2);
        System.out.println("¿Contiene requisito 3? " + tieneReq3);
        
        // Aserciones: deben estar los tres presentes
        Assert.assertTrue(tieneReq1, "No se encontró el requisito: " + requisito1);
        Assert.assertTrue(tieneReq2, "No se encontró el requisito: " + requisito2);
        Assert.assertTrue(tieneReq3, "No se encontró el requisito: " + requisito3);
        
        System.out.println("✅ Prueba exitosa: los tres requisitos de la Beca Bachiller están visibles.");
    }
    
    private void esperar(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @AfterTest
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
