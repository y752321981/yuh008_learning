package cn.edu.ujn.yuh008.controller;

import cn.edu.ujn.yuh008.common.Page;
import cn.edu.ujn.yuh008.common.Result;
import cn.edu.ujn.yuh008.pojo.entity.BookCatalog;
import cn.edu.ujn.yuh008.pojo.request.*;
import cn.edu.ujn.yuh008.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @PostMapping("/queryBookCatalog")
    private Result<Page<BookCatalog>> queryBookCatalog(@RequestBody BookCatalogQueryRequest request) {
        if (request == null) {
            return Result.success();
        }
        return Result.success(this.bookService.queryBookCatalog(request));
    }

    @PostMapping("/addBookCatalog")
    private Result<Map<String, String>> addBookCatalog(@RequestBody BookCatalog request) {
        if (request == null) {
            return Result.success("无请求体");
        }
        return Result.success(this.bookService.addBookCatalog(request));
    }

    @PostMapping("/deleteBookCatalog")
    private Result<Map<String, String>> deleteBookCatalog(@RequestBody BookCatalog request) {
        if (request == null) {
            return Result.success("无请求体");
        }
        return Result.success(this.bookService.deleteBookCatalog(request));
    }

    @PostMapping("/addBook")
    private Result<Map<String, String>> addBook(@RequestBody BookAddRequest request) {
        if (request == null) {
            return Result.success("无请求体");
        }
        return Result.success(this.bookService.addBook(request));
    }

    @PostMapping("/lendBook")
    private Result<Map<String, String>> lendBook(@RequestBody BookLendRequest request, HttpServletRequest httpServletRequest) {
        if (request == null) {
            return Result.success("无请求体");
        }
        return Result.success(this.bookService.lendBook(request, httpServletRequest));
    }

    @PostMapping("/returnBook")
    private Result<Map<String, String>> returnBook(@RequestBody BookReturnRequest request) {
        if (request == null) {
            return Result.success("无请求体");
        }
        return Result.success(this.bookService.returnBook(request));
    }

    @PostMapping("/deleteBook")
    private Result<Map<String, String>> deleteBook(@RequestBody BookDeleteRequest request) {
        if (request == null) {
            return Result.success("无请求体");
        }
        return Result.success(this.bookService.deleteBook(request));
    }
}
