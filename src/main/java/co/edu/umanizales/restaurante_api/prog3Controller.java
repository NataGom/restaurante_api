package co.edu.umanizales.restaurante_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prog3")
public class prog3Controller {
  @GetMapping
    public String ComidaRapida(){

        return "Comida Rapida";
    }
}
