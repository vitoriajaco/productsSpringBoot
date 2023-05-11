package cursoSpring.Products.rest.controller;

import cursoSpring.Products.entity.Cliente;
import cursoSpring.Products.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class ClienteController {



    private Clientes clientes;

    public ClienteController(Clientes clientes){
        this.clientes = clientes;

    }

    @GetMapping
    @ResponseBody
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

    @DeleteMapping(path = "/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity delete (@PathVariable Integer id) { //O delete só recebe um id no corpo da requisição
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()) {
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build(); //Se o código funcionar retorna "204 no content"
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping(path = "/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clientes.findById(id)
                .map(clienteExistente -> { //O Optional tem o método map, se existir o resultado, ele entra no map e executa
                    //o que está no map. Caso contrario ele retorna outra coisa.
                    cliente.setId(clienteExistente.getId()); //Troca o id para nao precisar passar todos os dados get
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build()); //O suplier nao recebe nada e retorna o que vc passar.

    }

    @GetMapping(path = "/api/clientes")
    public ResponseEntity find (Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher //permite que ele encontre os clientes atraves das propriedades
                .matching() //
                .withIgnoreCase() //Os valores que forem strings independentemente de estar maiusculo ou minusculo serão considerados na hora de encontrar
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //Forma que vai encontrar os valores String - em qualquer posição que esteja o valor ele ira ser retornado
        Example example = Example.of(filtro, matcher);
        List<Cliente> lista = clientes.findAll(example);
        return ResponseEntity.ok(lista);
    }
}
