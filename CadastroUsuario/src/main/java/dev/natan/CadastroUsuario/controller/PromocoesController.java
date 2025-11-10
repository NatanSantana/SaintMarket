package dev.natan.CadastroUsuario.controller;

import dev.natan.CadastroUsuario.entity.Promocoes;
import dev.natan.CadastroUsuario.service.PromocoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/promocoes")
public class PromocoesController {

    @Autowired
    private PromocoesService promocoesService;

    @PostMapping("/lancar-promocao")
    public Promocoes lancarPromocao(@RequestBody Promocoes promocoes, @RequestParam int duracaoHoras) {
        return promocoesService.lancarPromocao(promocoes, duracaoHoras);


    }





}
