package com.microservice_ecommerce.cart.cart;

import com.microservice_ecommerce.cart.cart.external.Product;

import java.util.Date;

public class CartItemResponse {

    protected Long id;

    private Product product;

    private Integer qty;

    private Double price;

    private Date createdAt;

    private Date updatedAt;

    public CartItemResponse(Long id, Product product, Integer qty, Double price, Date createdAt, Date updatedAt) {
        this.id = id;
        this.product = product;
        this.qty = qty;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CartItemResponse{" +
                "id=" + id +
                ", qty=" + qty +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
