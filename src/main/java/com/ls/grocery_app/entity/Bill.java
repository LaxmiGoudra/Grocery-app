package com.ls.grocery_app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")

public class Bill {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String customerName;
	    private Double totalAmount;
	    private LocalDateTime billDate;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public Double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public LocalDateTime getBillDate() {
			return billDate;
		}
		public void setBillDate(LocalDateTime billDate) {
			this.billDate = billDate;
		}

		@PrePersist
		protected void onCreate() {
	        billDate = LocalDateTime.now();
		}
}
