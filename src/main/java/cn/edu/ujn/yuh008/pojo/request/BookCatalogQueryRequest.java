package cn.edu.ujn.yuh008.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCatalogQueryRequest {
    private String id;
    private String bookName;
    private String author;
    private String describe;
    private Boolean storageNotNull;

    // 按浏览量排序,true 降序,false 升序,优先级高于发布日期
    private Boolean orderByViews;

    // 按发布日期排序, true 降序,false 升序
    private Boolean orderByPublishedDate;
    private String language;
    private String startPublishedDate;
    private String endPublishedDate;
    private Integer pageSize;
    private Integer pageNum;
}
