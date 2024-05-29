package com.backend.controller.model;

import jakarta.persistence.Column;

public class PaymentInformation {

	@Column(name="cardHolder_name")
	private String cardHolderName;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="expriation_Date")
	private String expirationDate;
	
	@Column(name="cvv")
	private String cvv;
}

