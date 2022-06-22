package cn.edu.ujn.yuh008.service;

import cn.edu.ujn.yuh008.common.Page;
import cn.edu.ujn.yuh008.pojo.entity.BookCatalog;
import cn.edu.ujn.yuh008.pojo.request.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IBookService {
    Page<BookCatalog> queryBookCatalog(BookCatalogQueryRequest request);

    Map<String, String> addBookCatalog(BookCatalog request);

    Map<String, String> lendBook(BookLendRequest request, HttpServletRequest httpServletRequest);

    Map<String, String> returnBook(BookReturnRequest request);

    Map<String, String> addBook(BookAddRequest request);

    Map<String, String> deleteBook(BookDeleteRequest request);

    Map<String, String> deleteBookCatalog(BookCatalog request);

    Map<String, String> updateBookCatalog(BookCatalog request);
}
