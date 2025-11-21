package com.example.sales_buy.controller;

import com.example.sales_buy.dto.info.ListingInfoDto;
import com.example.sales_buy.dto.post.SaleDetailPostDto;
import com.example.sales_buy.dto.post.SalePostDto;
import com.example.sales_buy.services.ListingService;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class MercadoPagoController {
    private final ListingService listingService;
    public MercadoPagoController(ListingService listingService){
        this.listingService=listingService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> createCheckoutPreference(@RequestBody SalePostDto salePostDto) {
        try {
            // Crear la preferencia de pago
            Preference preference = new Preference();

            // Lista para guardar los items
            ArrayList<Item> items = new ArrayList<>();

            // Para cada detalle, obtén el listing y su precio
            for (SaleDetailPostDto detail : salePostDto.getSaleDetailPostDtos()) {
                // Obtener información del listing que contiene el precio
                ListingInfoDto listing = listingService.getListingById(detail.getListingId());

                // Crear el item con la información obtenida
                Item item = new Item();
                item.setTitle("Carta ID: " + detail.getCardId());
                item.setQuantity(detail.getQuantity());
                item.setUnitPrice(listing.getUnitaryPrice().floatValue());
                items.add(item);
                System.out.println("Item -> title: " + item.getTitle() + ", quantity: " + item.getQuantity() + ", price: " + item.getUnitPrice());
            }

            preference.setItems(items);

            // Agregar metadata para identificar la orden después
            Map<String, String> metadata = new HashMap<>();
            metadata.put("buyer_id", String.valueOf(salePostDto.getBuyerId()));
            preference.setAdditionalInfo(metadata.toString());

            // Configurar URLs de redirección
            BackUrls backUrls = new BackUrls();
            backUrls.setSuccess("https://e22970b681c0.ngrok-free.app/cart");
            backUrls.setFailure("https://e22970b681c0.ngrok-free.app/cart");
            backUrls.setPending("https://e22970b681c0.ngrok-free.app/cart");
            preference.setBackUrls(backUrls);
            preference.setAutoReturn(Preference.AutoReturn.approved);
            // Guardar la preferencia para obtener el ID y URL de pago
            preference.save();
            System.out.println("Status: " + preference.getLastApiResponse().getStatusCode());
            System.out.println("Response body: " + preference.getLastApiResponse().getStringResponse());

            // Respuesta con los datos necesarios para redireccionar al usuario
            Map<String, String> response = new HashMap<>();
            response.put("init_point", preference.getInitPoint());
            response.put("preference_id", preference.getId());

            return ResponseEntity.ok(response);

        } catch (MPException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al crear preferencia de pago: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error inesperado: " + e.getMessage()));
        }

    }

}