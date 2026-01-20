package com.ls.grocery_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ls.grocery_app.entity.Product;
import com.ls.grocery_app.repository.ProductRepository;


@Controller
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @GetMapping("/")
    public String showProductList(Model model) {
        model.addAttribute("products", repo.findAll());
        return "index";
    }
        
        @GetMapping("/new")
        public String showNewProductForm(Model model) {
            Product product = new Product();
            model.addAttribute("product", product);
            return "add_product";
        }
        
        @PostMapping("/save")
        public String saveProduct(@ModelAttribute("product") Product product) {
            repo.save(product);
            return "redirect:/"; 
        }
        
        @GetMapping("/edit/{id}")
        public String showEditForm(@PathVariable("id") Long id, Model model) {
            Product product = repo.findById(id).get();
            model.addAttribute("product", product); 
            return "add_product"; 
        }
        
        @GetMapping("/delete/{id}")
        public String deleteProduct(@PathVariable("id") Long id) {
            repo.deleteById(id);
            return "redirect:/";
        }
    

    }


