package com.flab.s_market.shopping.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingProduct {
    @Id @GeneratedValue
    @Column(name = "shopping_product_id")
    private Long id;
    private int productAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_id")
    private Shopping shopping;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
