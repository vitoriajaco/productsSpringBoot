package cursoSpring.Products.rest.controller;

import cursoSpring.Products.entity.Cliente;
import cursoSpring.Products.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController //Usando o RestController nao se mostra mais necessario usar a anotação ResponseBody
@RequestMapping (path = "/api/clientes") // Passando o parametro aqui evita repetir em cima de cada metodo
public class ClienteController {



    private Clientes clientes;

    public ClienteController(Clientes clientes){
        this.clientes = clientes;

    }

    @GetMapping(path = "/{id}")
    public Cliente getClienteById(@PathVariable Integer id) {// Esse id vai estar na URL

            return clientes.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save (@RequestBody Cliente cliente) { //Esse (Cliente) vem no corpo da requisição
       return clientes.save(cliente);

    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Integer id) {
        clientes.findById(id).map(cliente -> { //metodo map necessita retornar algo
            clientes.delete(cliente);
            return cliente;
                })
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente Nao encontrado"));

    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Integer id, @RequestBody Cliente cliente) {
         clientes.findById(id)
                .map(clienteExistente -> { //O Optional tem o método map, se existir o resultado, ele entra no map e executa
                    //o que está no map. Caso contrario ele retorna outra coisa.
                    cliente.setId(clienteExistente.getId()); //Troca o id para nao precisar passar todos os dados get
                    clientes.save(cliente);
                    return clienteExistente;
                }) .orElseThrow(()
                         -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente Nao encontrado"));

    }

    @GetMapping
    public List<Cliente> find (Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher //permite que ele encontre os clientes atraves das propriedades
                .matching() //
                .withIgnoreCase() //Os valores que forem strings independentemente de estar maiusculo ou minusculo serão considerados na hora de encontrar
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //Forma que vai encontrar os valores String - em qualquer posição que esteja o valor ele ira ser retornado
        Example example = Example.of(filtro, matcher);
       return  clientes.findAll(example);

    }
}
