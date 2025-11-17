package dev.natan.CadastroUsuario.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import dev.natan.CadastroUsuario.Repository.ProdutosRepository;
import dev.natan.CadastroUsuario.Repository.UsuariosRepository;
import dev.natan.CadastroUsuario.entity.ItensDTO;
import dev.natan.CadastroUsuario.entity.Produtos;
import dev.natan.CadastroUsuario.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String token;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private ResgatarPromocaoService resgatarPromocaoService;


    public MercadoPagoService() {

        MercadoPagoConfig.setAccessToken(token);
    }

    public Preference criarCheckoutPro(List<ItensDTO> itensCompra, Long idUser)
            throws MPException, MPApiException {

        Optional<Usuarios> user = usuariosRepository.findById(idUser);


        // -----------------------------
        // ITEM DA COMPRA
        // -----------------------------
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (ItensDTO dto : itensCompra) {

            Optional<Produtos> produtos = produtosRepository.findById(dto.getIdProduto());

            BigDecimal precoFinal = resgatarPromocaoService.verificarPromocao(
                    produtos.get().getId(),
                    produtos.get().getCategoria()
            );



            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .id(String.valueOf(produtos.get().getId_produto()))
                    .title(produtos.get().getNomeProduto())
                    .description("Produto no SaintMarket")
                    .quantity(dto.getQuantidade())
                    .currencyId("BRL")
                    .unitPrice(new BigDecimal(String.valueOf(precoFinal)))

                    .build();

            items.add(item);
        }

        // -----------------------------
        // BACK URLS
        // -----------------------------
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://www.seu-site.com/success")
                .pending("https://www.seu-site.com/pending")
                .failure("https://www.seu-site.com/failure")
                .build();

        // -----------------------------
        // MÉTODOS DE PAGAMENTO
        // -----------------------------
        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(
                PreferencePaymentTypeRequest.builder().id("ticket").build()
        );
        excludedPaymentTypes.add(
                PreferencePaymentTypeRequest.builder().id("atm").build()
        );

        PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                .excludedPaymentTypes(excludedPaymentTypes)
                .installments(1) // Ex.: Número de parcelamentos disponíveis
                .build();

        // -----------------------------
        // PREFERÊNCIA FINAL
        // -----------------------------
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .payer(
                        PreferencePayerRequest.builder()
                                .name(user.get().getNome())
                                .email(user.get().getEmail())
                                .build()
                )
                .paymentMethods(paymentMethods)
                .autoReturn("approved")
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return preference;
    }
}
