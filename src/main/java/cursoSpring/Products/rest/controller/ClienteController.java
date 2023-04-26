package cursoSpring.Products.rest.controller;

import cursoSpring.Products.entity.Cliente;
import cursoSpring.Products.repository.Clientes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ClienteController {



    private Clientes clientes;

    public ClienteController(Clientes clientes){
        this.clientes = clientes;

    }

    @GetMapping
    public String metodo(){
        return "qualquer coisa";
    }


    @GetMapping(path = "/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id) {// Esse id vai estar na URL
        Optional<Cliente> cliente = clientes.findById(id);
        if (cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/api/clientes")
    @ResponseBody //Resposta da Requisição não confundir com o Request que vem na requisição que é o que recebe
    public ResponseEntity save (@RequestBody Cliente cliente) { //Esse (Cliente) vem no corpo da requisição
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo); //Ainda nao esta no modelo restfull 

    }

}
