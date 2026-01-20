package com.ls.grocery_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.ls.grocery_app.entity.*;
import com.ls.grocery_app.repository.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Controller
public class BillController {

	 @Autowired
	    private ProductRepository productRepo;
	    
	    @Autowired
	    private BillRepository billRepo;
	    
	    @GetMapping("/create-bill")
	    public String showBillPage(Model model) {
	    List<Product> products = productRepo.findAll();	
	    model.addAttribute("products", products); 
        model.addAttribute("bill", new Bill());
        return "create_bill";
    }
	    @PostMapping("/save-bill")
	    public String saveBill(@RequestParam("productId") Long productId,
	    		@RequestParam("quantity") Integer quantity,
	    		@RequestParam("customerName") String customerName, Model model) {
	    	  Product product = productRepo.findById(productId).get();
	          if (product.getStock() < quantity) {
	              model.addAttribute("error", "Out of Stock! Only " + product.getStock() + " left.");
	              model.addAttribute("products", productRepo.findAll());
	              return "create_bill"; 
	          }
	       
	          double total = product.getPrice() * quantity;
	          
	          Bill bill = new Bill();
	          bill.setCustomerName(customerName);
	          bill.setTotalAmount(total);
	          billRepo.save(bill);
	          
	          product.setStock(product.getStock() - quantity);
	          productRepo.save(product);
	          return "redirect:/";
	    }
	    @GetMapping("/bills")
	    public String viewBillHistory(Model model) {
	        List<Bill> allBills = billRepo.findAll(); // ಎಲ್ಲಾ ಬಿಲ್ ತರಿಸಿಕೊ
	        model.addAttribute("bills", allBills);
	        return "bill_history"; // bill_history.html ಪೇಜ್ ತೋರಿಸು
	    }
	    
	    @GetMapping("/download-pdf/{id}")
	    public void downloadPdf(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, DocumentException {
	        
	        Bill bill = billRepo.findById(id).get();

	        
	        response.setContentType("application/pdf");
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=Bill_" + bill.getId() + ".pdf";
	        response.setHeader(headerKey, headerValue);

	        // 3. PDF ಬರೆಯುವುದು
	        Document document = new Document();
	        PdfWriter.getInstance(document, response.getOutputStream());

	        document.open();
	        
	        // ಫಾಂಟ್ ಸ್ಟೈಲ್
	        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        fontTitle.setSize(18);

	        // ಟೈಟಲ್
	        Paragraph title = new Paragraph("GROCERY STORE RECEIPT", fontTitle);
	        title.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(title);
	        document.add(new Paragraph("------------------------------------------------"));

	        // ವಿವರಗಳು
	        document.add(new Paragraph("Bill ID: " + bill.getId()));
	        document.add(new Paragraph("Customer Name: " + bill.getCustomerName()));
	        document.add(new Paragraph("Date: " + bill.getBillDate()));
	        document.add(new Paragraph("------------------------------------------------"));
	        
	        // ದೊಡ್ಡ ಅಕ್ಷರದಲ್ಲಿ ಮೊತ್ತ
	        Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        fontTotal.setSize(14);
	        document.add(new Paragraph("Total Amount Paid: Rs. " + bill.getTotalAmount(), fontTotal));
	        
	        document.add(new Paragraph("------------------------------------------------"));
	        document.add(new Paragraph("Thank you for shopping with us!"));

	        document.close();
	    }
	    }
	    

