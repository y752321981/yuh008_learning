package cn.edu.ujn.yuh008.pojo.request;

import cn.edu.ujn.yuh008.pojo.entity.BookCatalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAddRequest {
    private String bookCatalogId;
}
