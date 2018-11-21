package io.spring.lab.store.special;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SpecialCalculation {

	private String specialId;

	private BigDecimal totalPrice;
}
