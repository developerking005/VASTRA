package com.backend.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {	
	private String paymentMethod;	
	private String status;
	private String paymentId;
	private String razorPaymentLinkId;
	private String razorPaymentLinkReferenceId;
	private String razorPaymentLinkStatus;
	private String razorPaymentId;
	
	

}
