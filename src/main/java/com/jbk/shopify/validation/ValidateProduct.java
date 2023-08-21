package com.jbk.shopify.validation;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jbk.shopify.model.Category;
import com.jbk.shopify.model.Product;
import com.jbk.shopify.model.Supplier;
import com.jbk.shopify.service.CategoryService;
import com.jbk.shopify.service.SupplierService;

@Component
public class ValidateProduct {
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private CategoryService categoryService;
	
	public Map<String, String> validateProduct(Product product) {
		System.out.println(1111);
		Map<String, String> errorMap=new HashedMap<String, String>();
		if(product.getProductName()==null) {
			errorMap.put("Product Name", "Invalid Product Name");
			
			System.out.println(222);
		}
		if(product.getSupplier().getSupplierId()>0) {
			Supplier supplier = supplierService.getSupplierById(product.getSupplier().getSupplierId());
			if(supplier==null) {
				errorMap.put("Supplier", "Supplier Not Exists");
			}
		}else {
			errorMap.put("Supplier", "Invalid Supplier");
		}
		
		if(product.getCategory().getCategoryId()>0) {
			Category category = categoryService.getCategoryById(product.getCategory().getCategoryId());
			if(category==null) {
				errorMap.put("Category", "Category Not Exists");
			}
		}else {
			errorMap.put("Category", "Invalid Category");
		}
		
		if(product.getProductQty()<=0) {
			errorMap.put("Product Qty", "Product Qty should be greater than 0");
		}
		
		if(product.getProductPrice()<=0) {
			errorMap.put("Product Price", "Product Price should be greater than 0");
		
		}
		
		return errorMap;
		
	}

}
