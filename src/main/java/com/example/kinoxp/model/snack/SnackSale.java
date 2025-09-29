package com.example.kinoxp.model.snack;

import com.example.kinoxp.model.employee.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnackSale {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleId;
    
    private LocalDateTime dateTime;
    private BigDecimal totalPrice;
    
    @ManyToOne
    private Employee salesClerk;
    
    @ElementCollection
    @CollectionTable(name = "SnackSaleItem", joinColumns = @JoinColumn(name = "SaleID"))
    @MapKeyJoinColumn(name = "ItemID")
    @Column(name = "Quantity")
    private Map<SnackItem, Integer> items;
}