package com.example.kinoxp.controller;

import com.example.kinoxp.model.snack.SnackSale;
import com.example.kinoxp.model.snack.SnackItem;
import com.example.kinoxp.service.snack.SnackSaleService;
import com.example.kinoxp.service.snack.SnackItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/snack-sales")
@CrossOrigin(origins = "*")
public class SnackSaleController {

    private final SnackSaleService snackSaleService;
    private final SnackItemService snackItemService;

    public SnackSaleController(SnackSaleService snackSaleService, SnackItemService snackItemService) {
        this.snackSaleService = snackSaleService;
        this.snackItemService = snackItemService;
    }

    @PostMapping
    public ResponseEntity<SnackSale> createSale(@RequestBody SnackSale snackSale) {
        if (snackSale.getDateTime() == null) {
            snackSale.setDateTime(LocalDateTime.now());
        }

        // Validere alle items eksisterer og udregner samlet pris
        double calculatedTotal = 0.0;
        for (var entry : snackSale.getItems().entrySet()) {
            SnackItem item = entry.getKey();
            Integer quantity = entry.getValue();


            SnackItem dbItem = snackItemService.findById(item.getItemId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Snack item with ID " + item.getItemId() + " not found"
                    ));


            if (dbItem.getStock() < quantity) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Insufficient stock for " + dbItem.getName() +
                                ". Available: " + dbItem.getStock() + ", Requested: " + quantity
                );
            }

            calculatedTotal += dbItem.getPrice() * quantity;


            dbItem.setStock(dbItem.getStock() - quantity);
            snackItemService.save(dbItem);
        }

        snackSale.setTotalPrice(calculatedTotal);
        SnackSale savedSale = snackSaleService.save(snackSale);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSale);
    }

    @GetMapping
    public ResponseEntity<List<SnackSale>> getAllSales() {
        return ResponseEntity.ok(snackSaleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SnackSale> getSale(@PathVariable Integer id) {
        SnackSale sale = snackSaleService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Snack sale with ID " + id + " not found"
                ));
        return ResponseEntity.ok(sale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SnackSale> updateSale(@PathVariable Integer id, @RequestBody SnackSale snackSale) {
        SnackSale existing = snackSaleService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Snack sale with ID " + id + " not found"
                ));

        snackSale.setSaleId(existing.getSaleId()); // ensure ID consistency
        SnackSale updatedSale = snackSaleService.save(snackSale);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Integer id) {
        if (!snackSaleService.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Snack sale with ID " + id + " not found"
            );
        }
        snackSaleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}