package com.example.kinoxp.controller;


import com.example.kinoxp.model.snack.SnackItem;
import com.example.kinoxp.service.snack.SnackItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/snack-items")
@CrossOrigin(origins = "*")
public class SnackItemController {

    private final SnackItemService snackItemService;

    public SnackItemController(SnackItemService snackItemService) {
        this.snackItemService = snackItemService;
    }

    @GetMapping
    public ResponseEntity<List<SnackItem>> getAllItems() {
        return ResponseEntity.ok(snackItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SnackItem> getItem(@PathVariable Integer id) {
        SnackItem item = snackItemService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Snack item with ID " + id + " not found"
                ));
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<SnackItem> createItem(@RequestBody SnackItem snackItem) {
        SnackItem savedItem = snackItemService.save(snackItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SnackItem> updateItem(@PathVariable Integer id, @RequestBody SnackItem snackItem) {
        if (!snackItemService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Snack item not found");
        }
        snackItem.setItemId(id);
        return ResponseEntity.ok(snackItemService.save(snackItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        if (!snackItemService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Snack item not found");
        }
        snackItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}