package cn.edu.ujn.yuh008.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCatalog {
    private String id;
    private String bookName;
    private String author;
    private Integer total;
    private Integer storage;
    private Integer lend;
    private Integer views;
    private String description;
    private String language;
    private String publishedDate;
}
