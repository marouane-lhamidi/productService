package com.example.productservice.common.utiles;

public class ApisPath {

    private ApisPath() {
    }

    public static final String GET_ALL_BRANDS = "/brands";
    public static final String ADD_BRAND = "/addBrand";
    public static final String UPDATE_BRAND = "/updateBrand";
    public static final String DELETE_BRAND = "/deleteBrand/{id}";
    public static final String GET_BRAND_BY_NAME = "/getBrand/{name}";

    public static final String GET_ALL_TAGS = "/tags";
    public static final String ADD_TAG = "/addTag";
    public static final String UPDATE_TAG = "/updateTag";
    public static final String DELETE_TAG = "/deleteTag/{id}";
    public static final String GET_TAG_BY_NAME = "/getTag/{name}";

    public static final String GET_ALL_CATEGORIES = "/categories";
    public static final String ADD_CATEGORY = "/addCategory";
    public static final String UPDATE_CATEGORY = "/updateCategory";
    public static final String DELETE_CATEGORY = "/deleteCategory/{id}";
    public static final String GET_CATEGORY_BY_NAME = "/getCategory/{name}";

    public static final String GET_ALL_PRODUCTS = "/products";
    public static final String ADD_PRODUCT = "/addProduct";
    public static final String UPDATE_PRODUCT = "/updateProduct";
    public static final String DELETE_PRODUCT = "/deleteProduct/{id}";
    public static final String GET_PRODUCT_BY_NAME = "/getProduct/{name}";
    public static final String GET_PRODUCT_IMAGES = "/getProductImages/{name}";
    public static final String GET_IMAGE = "/getImage/{filename}";
}
