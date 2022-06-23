package cn.edu.ujn.yuh008.dao;

import cn.edu.ujn.yuh008.pojo.entity.BookCatalog;
import cn.edu.ujn.yuh008.pojo.entity.BookItem;
import cn.edu.ujn.yuh008.pojo.request.BookCatalogQueryRequest;
import cn.edu.ujn.yuh008.pojo.request.BookLendRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao {

    List<BookCatalog> queryBookCatalog(BookCatalogQueryRequest request);

    BookItem queryBook(@Param("bookItemId")String bookItemId);

    void lendBook(BookLendRequest request);

    void returnBook(@Param("bookItemId")String bookItemId);

    void insertBookItem(BookItem bookItem);

    void insertBookCatalog(BookCatalog bookCatalog);

    void deleteBookCatalog(BookCatalog bookCatalog);

    void deleteBook(@Param("bookItemId")String bookItemId);

    void updateBookCatalog(BookCatalog request);

    List<BookItem> queryBookByCatalogId(@Param("bookCatalogId")String bookCatalogId);
}
