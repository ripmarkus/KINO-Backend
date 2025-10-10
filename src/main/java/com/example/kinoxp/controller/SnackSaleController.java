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
import java.util.Map;

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
    public ResponseEntity<SnackSale> createSale(@RequestBody Map<String, Object> payload) {
        try {
            // Extract items from payload (format: { "items": { "1": 2, "3": 1 } })
            @SuppressWarnings("unchecked")
            Map<String, Integer> itemsMapRaw = (Map<String, Integer>) payload.get("items");

            if (itemsMapRaw == null || itemsMapRaw.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Sale must contain at least one item"
                );
            }

            // Set current time
            LocalDateTime saleTime = LocalDateTime.now();

            // Validate all items and calculate total price
            double calculatedTotal = 0.0;
            Map<SnackItem, Integer> itemsToSave = new java.util.HashMap<>();

            for (var entry : itemsMapRaw.entrySet()) {
                Integer itemId = Integer.parseInt(entry.getKey()); // Convert string key to integer
                Integer quantity = entry.getValue();

                // Validate quantity
                if (quantity == null || quantity <= 0) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Quantity must be greater than 0"
                    );
                }

                // Fetch item from database
                SnackItem dbItem = snackItemService.findById(itemId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Snack item with ID " + itemId + " not found"
                        ));

                // Check stock availability
                if (dbItem.getStock() < quantity) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Insufficient stock for " + dbItem.getName() +
                                    ". Available: " + dbItem.getStock() + ", Requested: " + quantity
                    );
                }

                // Calculate price for this item
                calculatedTotal += dbItem.getPrice() * quantity;

                // Update stock
                dbItem.setStock(dbItem.getStock() - quantity);
                snackItemService.save(dbItem);

                // Store item for the sale
                itemsToSave.put(dbItem, quantity);
            }

            // Create and save the sale
            SnackSale snackSale = new SnackSale();
            snackSale.setDateTime(saleTime);
            snackSale.setTotalPrice(calculatedTotal);
            snackSale.setItems(itemsToSave);

            SnackSale savedSale = snackSaleService.save(snackSale);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSale);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace(); // Print full error to console
            System.err.println("Sale processing error: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error processing sale: " + e.getMessage()
            );
        }
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

        snackSale.setSaleId(existing.getSaleId());
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