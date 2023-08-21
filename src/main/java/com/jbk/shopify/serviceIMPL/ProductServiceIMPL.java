package com.jbk.shopify.serviceIMPL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jbk.shopify.dao.ProductDao;
import com.jbk.shopify.model.Category;
import com.jbk.shopify.model.Charges;
import com.jbk.shopify.model.FinalProduct;
import com.jbk.shopify.model.Product;
import com.jbk.shopify.model.Supplier;
import com.jbk.shopify.service.ProductService;
import com.jbk.shopify.validation.ValidateProduct;

@Service
public class ProductServiceIMPL implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ValidateProduct validateProduct;

	@Autowired
	private FinalProduct finalProduct;
	@Autowired
	private Charges charges;

	Map<String, String> validateError = new HashedMap<>();

	Map<Integer, Map<String, String>> errorMap = new HashedMap<Integer, Map<String, String>>();

	Map<String, Object> finalMap = new HashedMap<String, Object>();
	List<Integer> alreadyExistsRows=new ArrayList<Integer>();
	int totalRecords=0;

	@Override
	public int saveProduct(Product product) {

		String productId = new SimpleDateFormat("yyyyMMddHHmmsss").format(new java.util.Date());

		product.setProductId(Long.parseLong(productId));

		int uploadedCount = productDao.saveProduct(product);

		return uploadedCount;
	}

	@Override
	public Product getProductById(long productId) {

		return productDao.getProductById(productId);
	}

	@Override
	public List<Product> getAllProduct() {

		return productDao.getAllProduct();
	}

	@Override
	public boolean deleteProductById(long productId) {

		return productDao.deleteProductById(productId);
	}

	@Override
	public boolean updateProduct(Product product) {

		return productDao.updateProduct(product);
	}

	@Override
	public List<Product> getAllProductByOrder_Field(String orderType, String name) {

		return productDao.getAllProductByOrder_Field(orderType, name);
	}

	@Override
	public List<Product> getMaxPriceProducts() {

		return productDao.getMaxPriceProducts();
	}

	@Override
	public List<Product> getProductsContainsWith(String subStringOfProduct) {

		return productDao.getProductsContainsWith(subStringOfProduct);
	}

	@Override
	public int getTotalCountOfProducts() {

		return productDao.getTotalCountOfProducts();
	}

	@Override
	public List<Product> getProductsMoreThanGivenPrice(double price) {

		return productDao.getProductsMoreThanGivenPrice(price);
	}

	@Override
	public FinalProduct getFinalProductById(long id) {

		Product product = getProductById(id);

		if (product != null) {
			finalProduct.setProductId(id);
			finalProduct.setProductName(product.getProductName());
			finalProduct.setProductQty(product.getProductQty());
			finalProduct.setProductPrice(product.getProductPrice());
			finalProduct.setSupplier(product.getSupplier());
			finalProduct.setCategory(product.getCategory());

			double productPrice = product.getProductPrice();
			int gst = product.getCategory().getGst();
			float deliveryCharges = product.getCategory().getDeliveryCharge();
			int discount = product.getCategory().getDiscount();

			double gstAmount = productPrice * gst / 100;
			charges.setGstAmount(gstAmount);
			charges.setDeliveryCharges(deliveryCharges);
			finalProduct.setCharges(charges);

			double discountAmout = productPrice * discount / 100;
			finalProduct.setDiscountAmount(discountAmout);

			finalProduct.setFinalProductPrice((productPrice + deliveryCharges + gstAmount) - discountAmout);

		}

		return finalProduct;
	}

	private List<Product> readExcel(String path) {
		List<Product> list = new ArrayList<Product>();
		try {
			String fileExtension = StringUtils.getFilenameExtension(path);
			System.out.println(fileExtension);

			if ("xlsx".equals(fileExtension)) {
				try (FileInputStream fis = new FileInputStream(path); Workbook workbook = new XSSFWorkbook(fis);) {
					Sheet sheet = workbook.getSheetAt(1);
					 totalRecords=sheet.getLastRowNum();

					Iterator<Row> rows = sheet.rowIterator();

					while (rows.hasNext()) {
						Row row = (Row) rows.next();

						int rowNum = row.getRowNum();
						if (rowNum == 0) {
							continue;
						}

						Product product = new Product();
						String productId = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

						product.setProductId(Long.parseLong(productId + rowNum));

						Iterator<Cell> cells = row.cellIterator();

						while (cells.hasNext()) {
							Cell cell = (Cell) cells.next();

							int columnIndex = cell.getColumnIndex();
							switch (columnIndex) {
							case 0: {

								product.setProductName(cell.getStringCellValue());
								break;
							}

							case 1: {
								Supplier supplier = new Supplier();
								supplier.setSupplierId((long) cell.getNumericCellValue());
								product.setSupplier(supplier);
								break;
							}

							case 2: {
								Category category = new Category();
								category.setCategoryId((long) cell.getNumericCellValue());
								product.setCategory(category);
								break;
							}

							case 3: {
								product.setProductQty((int) cell.getNumericCellValue());
								break;
							}

							case 4: {
								product.setProductPrice(cell.getNumericCellValue());
								break;
							}

							default:
								break;
							}

						}

						validateError = validateProduct.validateProduct(product);

						if (validateError == null || validateError.isEmpty()) {
								
							Product productModel = getProductByName(product.getProductName());
							if(productModel==null) {
								list.add(product);
							}else {
								alreadyExistsRows.add(row.getRowNum()+1);
							}
							
						} else {
							errorMap.put(rowNum+1, validateError);
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	@Override
	public Map<String, Object> uploadSheet(MultipartFile file) {
		String folderPath = "src/main/resources";
		String fileName = file.getOriginalFilename();
		int uploadedCount = 0;
		try (FileOutputStream fos = new FileOutputStream(folderPath + File.separator + fileName)) {

			byte[] data = file.getBytes();
			fos.write(data);

			List<Product> list = readExcel(folderPath + File.separator + fileName);

			for (Product product : list) {
				 uploadedCount = uploadedCount+ productDao.saveProduct(product);	

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finalMap.put("Uploaded Records In DB", uploadedCount);
		finalMap.put("Total Exists Records In DB", alreadyExistsRows.size());
		finalMap.put("Total Record In Sheet", totalRecords);
		finalMap.put("Excluded Records Count", errorMap.size());
		finalMap.put("Bad Records", errorMap);
		finalMap.put("Row num, Exists in db",alreadyExistsRows);

		return finalMap;
	}

	@Override
	public Product getProductByName(String productName) {
		
		return productDao.getProductByName(productName);
	}

}
