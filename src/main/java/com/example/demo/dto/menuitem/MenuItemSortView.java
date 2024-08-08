package com.example.demo.dto.menuitem;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemSortView {
    private int menuItemId;
    private String menuItemName;
    private MenuItemCategoryView menuItemCategory;
    private String description;
    private String imageUrl;
//    @JsonProperty("isActive")
//    private boolean isActive;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Ẩn field này khi serializing
    private boolean isActive;

    @JsonGetter("isActive")
    public String getIsActiveAsString() {
        return isActive ? "Disable" : "Enable";
    }

}
