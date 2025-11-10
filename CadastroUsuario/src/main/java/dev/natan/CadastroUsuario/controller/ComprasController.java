package dev.natan.CadastroUsuario.controller;

import dev.natan.CadastroUsuario.entity.Compras;
import dev.natan.CadastroUsuario.service.ComprasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class ComprasController {


    private final ComprasService comprasService;


    public ComprasController(ComprasService comprasService) {
        this.comprasService = comprasService;
    }

    @GetMapping("/comprasFeitas")
    public List<Compras> listarCompras() {
        return comprasService.listarCompras();
    }



    @PostMapping("/registrarCompra/{idProduto}/{qtdeProdutos}") /* É possível colocar um cpf depois da qtdeProdutos EX: POST /registrarCompra/5/3?cpf=12345678900 */
    public Compras registrar(@PathVariable Long idProduto,
                             @PathVariable int qtdeProdutos,@RequestParam(required = false) String cpf) {

        return comprasService.registrarCompra(idProduto, qtdeProdutos, cpf);
    }

    @GetMapping("/ComprasFeitas/por")
    public List<Compras> listarComprasByCpf(@RequestParam String cpf) {
       return comprasService.listarComprasFeitasPorCpf(cpf);
    }


    @DeleteMapping("/cancelar-compra")
    public void deletarCompraFeita(@RequestParam Long id) {

        comprasService.cancelarCompraFeita(id);
    }


}
