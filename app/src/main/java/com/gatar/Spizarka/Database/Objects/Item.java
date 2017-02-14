package com.gatar.Spizarka.Database.Objects;

import com.gatar.Spizarka.Database.Categories;

import java.io.Serializable;

/**
 * Item object,
 * containing values:
 * <ol>
 *      <li>id {@link Integer}</li>
 *      <li>title {@link String}</li>
 *      <li>category {@link Categories}</li>
 *      <li>quantity {@link Integer}</li>
 *      <li>minimum quantity {@link Integer}</li>
 *      <li>description {@link String}</li>
 *</ol>
 *
 * Constructor without parameters set values as:
 * <ol>
 *      <li>integers: 0</li>
 *      <li>strings: "" (empty, but not null)</li>
 *      <li>category: Other</li>
 * </ol>
 *
 * Setters check quantity and minimum quantity values, in case of set values below zero, saved value will be zero.
 */
public class Item implements Serializable {

    private Long id;

    private String title;
    private Categories category;
    private Integer quantity;
    private Integer minimumQuantity;
    private String description;

    public Item (){
        this.title = "";
        this.category = Categories.Other;
        this.quantity = 0;
        this.minimumQuantity = 0;
        this.description = "";
    }

    public Item(String nazwa, Categories category, int quantity, int minimumQuantity, String description) {
        this.title = nazwa;
        this.category = category;
        this.quantity = (quantity < 0) ? 0 : quantity;
        this.minimumQuantity = (minimumQuantity < 0)? 0 : minimumQuantity;
        this.description = description;
    }

    /**
     * Get id of item. Id is unique key-value for both databases.
     * @return id value.
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get title of item. Title is unique value for each item.
     * @return title of item.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String nazwa) {
        this.title = nazwa;
    }

    /**
     * Get item category based of {@link Categories}
     * @return item category.
     */
    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    /**
     * Get quantity of item which are in depot.
     * @return number of items on stock.
     */
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = (quantity < 0) ? 0 : quantity;
    }

    /**
     * Get minimum quantity of item, which are used to create Shopping List
     * @return number of minimum items which should be on stock
     */
    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = (minimumQuantity < 0)? 0 : minimumQuantity;
    }

    /**
     * Get String with description bind to item.
     * @return item description.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Create {@link ItemDTO} object for send it to the remote database.
     * @return Item as ItemDTO object.
     */
    public ItemDTO toItemDTO(){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitle(title);
        itemDTO.setCategory(category.name());
        itemDTO.setDescription(description);
        itemDTO.setMinimumQuantity(minimumQuantity);
        itemDTO.setQuantity(quantity);
        itemDTO.setIdItemAndroid(id);
        return itemDTO;
    }

    public EntityDTO toEntityDTO(){
        EntityDTO entityDTO = new EntityDTO();
        entityDTO.setCategory(category.name());
        entityDTO.setDescription(description);
        entityDTO.setIdItemAndroid(id);
        entityDTO.setMinimumQuantity(minimumQuantity);
        entityDTO.setQuantity(quantity);
        entityDTO.setTitle(title);
        return entityDTO;
    }
}
