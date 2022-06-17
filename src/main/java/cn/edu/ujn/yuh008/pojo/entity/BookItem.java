package cn.edu.ujn.yuh008.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookItem {
    private String id;
    private String bookCatalogId;
    private Integer status;
}
