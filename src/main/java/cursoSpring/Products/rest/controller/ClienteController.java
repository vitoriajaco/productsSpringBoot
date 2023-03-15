package cursoSpring.Products.rest.controller;

import cursoSpring.Products.entity.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class ClienteController {

    @RequestMapping(value = "/api/clientes/hello/{nome}",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"}, // O cliente poderá uma requisição tanto json quanto xml
             produces= {"application/json", "application/xml"}
    )
    @ResponseBody
    public String helloCliente (@PathVariable ("nome") String nomeCliente, @RequestBody Cliente cliente){
        return String.format("Hello %s ", nomeCliente);
    }

}
