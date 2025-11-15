package dev.natan.CadastroUsuario.controller;

import com.mercadopago.resources.preference.Preference;
import dev.natan.CadastroUsuario.entity.ItensDTO;
import dev.natan.CadastroUsuario.service.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping("/checkout-pro")
    public ResponseEntity<?> criarCheckout(
            @RequestBody List<ItensDTO> itensCompra,
            @RequestParam Long idUser
    ) {
        try {
            Preference preference = mercadoPagoService.criarCheckoutPro(itensCompra, idUser);

            return ResponseEntity.ok().body(
                    Map.of(
                            "init_point", preference.getInitPoint(),
                            "sandbox_init_point", preference.getSandboxInitPoint()
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }
}
