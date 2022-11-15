package mgarzon.createbest.productcatalog;


/**
 * Created by Miguel Garzón on 2017-05-07.
 */

public class Product {
    private double _id;
    private String _productname;
    private double _price;
    private int _sku;

    public Product() {
    }
    public Product(double id, String productname, double price) {
        _id = id;
        _productname = productname;
        _price = price;

    }
    public Product(String productname, double price) {
        _productname = productname;
        _price = price;
    }

    public void setId(int id) {
        _id = id;
    }
    public double getId() {
        return _id;
    }
    public void setProductName(String productname) {
        _productname = productname;
    }
    public String getProductName() {
        return _productname;
    }
    public void setPrice(double price) {
        _price = price;
    }
    public double getPrice() {
        return _price;
    }
    public int getSku() {
        return _sku;
    }
    public void setSku(int _sku) {
        this._sku = _sku;
    }
}


