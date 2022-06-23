package cn.edu.ujn.yuh008.service.impl;

import cn.edu.ujn.yuh008.common.Page;
import cn.edu.ujn.yuh008.dao.BookDao;
import cn.edu.ujn.yuh008.dao.LoginDao;
import cn.edu.ujn.yuh008.pojo.entity.BookCatalog;
import cn.edu.ujn.yuh008.pojo.entity.BookItem;
import cn.edu.ujn.yuh008.pojo.entity.Token;
import cn.edu.ujn.yuh008.pojo.request.*;
import cn.edu.ujn.yuh008.service.IBookService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private LoginDao loginDao;

    @Override
    public Page<BookCatalog> queryBookCatalog(BookCatalogQueryRequest request) {

        Page<BookCatalog> books = new Page<>(request.getPageNum(), request.getPageSize());
        List<BookCatalog> list = this.bookDao.queryBookCatalog(request);
        books.setList(list);
        books.setMsg("共获取目录" + books.getTotal() + "项");
        return books;
    }

    @Override
    public Map<String, String> updateBookCatalog(BookCatalog request) {
        Map<String, String> result = new HashMap<>();
        try {
            this.bookDao.updateBookCatalog(request);
            result.put("msg", "更新书目成功");
        } catch (Exception e) {
            result.put("msg", "更新书目异常");
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Override
    public Map<String, String> addBookCatalog(BookCatalog request) {
        Map<String, String> result = new HashMap<>();
        if (request.getBookName() == null || "".equals(request.getBookName())) {
            result.put("msg", "书名不能为空");
            return result;
        }
        BookCatalogQueryRequest bookCatalogQueryRequest = new BookCatalogQueryRequest();
        bookCatalogQueryRequest.setBookName(request.getBookName());
        bookCatalogQueryRequest.setAuthor(request.getAuthor());
        bookCatalogQueryRequest.setLanguage(request.getLanguage());
        List<BookCatalog> bookCatalogs = this.bookDao.queryBookCatalog(bookCatalogQueryRequest);
        if (bookCatalogs != null || bookCatalogs.size() > 0) {
            List<BookCatalog> collect = bookCatalogs.stream().filter(item -> item.getBookName().equals(request.getBookName()) && item.getAuthor().equals(request.getAuthor()) && item.getLanguage().equals(request.getLanguage()) && item.getPublishedDate().equals(request.getPublishedDate())).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                result.put("msg", "与已收录图书信息重复");
                result.put("catalog", JSONObject.toJSONString(collect));
                return result;
            }
        }
        request.setId(UUID.randomUUID().toString());
        try {
            this.bookDao.insertBookCatalog(request);
            result.put("msg", "成功收录图书");
        } catch (Exception e) {
            result.put("msg", "收录图书异常");
            result.put("error", e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, String> deleteBookCatalog(BookCatalog request) {
        Map<String, String> result = new HashMap<>();
        if (request.getId() == null || "".equals(request.getId()) || request.getBookName() == null || "".equals(request.getBookName())) {
            result.put("msg", "请输入完整的目录编码和书名来执行删除操作");
            return result;
        }
        BookCatalogQueryRequest bookCatalogQueryRequest = new BookCatalogQueryRequest();
        bookCatalogQueryRequest.setId(request.getId());
        bookCatalogQueryRequest.setBookName(request.getBookName());
        List<BookCatalog> bookCatalogs = this.bookDao.queryBookCatalog(bookCatalogQueryRequest);
        if (bookCatalogs == null || bookCatalogs.size() == 0) {
            result.put("msg", "书名输入不正确");
        } else {
            try {
                this.bookDao.deleteBookCatalog(request);
                result.put("msg", "已删除该目录条，并删除其下所有图书");
            } catch (Exception e) {
                e.printStackTrace();
                result.put("msg", "删除目录时异常");
                result.put("error", e.getMessage());
            }
        }



        return result;
    }

    @Override
    public Page<BookItem> queryBookItem(BookQueryRequest request) {
        Page<BookItem> books = new Page<>(request.getPageNum(), request.getPageSize());
        List<BookItem> bookItems = this.bookDao.queryBookByCatalogId(request.getBookCatalogId());
        bookItems.forEach(item -> {
            switch (item.getStatus()) {
                case 0:
                    item.setStatusMsg("正常");
                    break;
                case 1:
                    item.setStatusMsg("租借中");
                    break;
                case 2:
                    item.setStatusMsg("已报废");
                    break;
            }
        });
        books.setList(bookItems);
        books.setMsg("共获取图书" + books.getTotal() + "项");
        return books;
    }

    @Override
    public Map<String, String> lendBook(BookLendRequest request, HttpServletRequest httpServletRequest) {
        Map<String, String> result = new HashMap<>();
        BookItem bookItem = this.bookDao.queryBook(request.getBookItemId());
        if (bookItem == null) {
            result.put("msg", "该图书未入库");
            return result;
        }
        try {
            if (new SimpleDateFormat("yyyy-MM-dd").parse(request.getLastReturnTime()).before(new Date())) {
                result.put("msg", "归还日期不能早于明天");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            result.put("msg", "日期格式出错");
            result.put("error", e.getMessage());
            return result;
        }
        switch (bookItem.getStatus()) {
            case 0:
                request.setBookLendId(UUID.randomUUID().toString());
                Token token = this.loginDao.queryToken(httpServletRequest.getHeader("token"));
                request.setUsername(token.getUsername());
                try {
                    this.bookDao.lendBook(request);
                    result.put("msg", "租借成功，请按时归还");
                } catch (Exception e) {
                    e.printStackTrace();
                    result.put("msg", "租借异常");
                    result.put("error", e.getMessage());
                }
                break;
            case 1:
                result.put("msg", "该图书正被租借中，请先归还");
                break;
            case 2:
                result.put("msg", "该图书已作废");
                break;
        }
        return result;
    }

    @Override
    public Map<String, String> addBook(BookAddRequest request) {
        Map<String, String> result = new HashMap<>(10);
        BookCatalogQueryRequest bookCatalogQueryRequest = new BookCatalogQueryRequest();
        bookCatalogQueryRequest.setId(request.getBookCatalogId());
        List<BookCatalog> list = this.bookDao.queryBookCatalog(bookCatalogQueryRequest);
        if (list == null || list.size() == 0) {
            result.put("msg", "目录中未收录该图书，请先录入");
        } else {
            BookItem bookItem = new BookItem();
            bookItem.setId(UUID.randomUUID().toString());
            bookItem.setBookCatalogId(request.getBookCatalogId());
            bookItem.setStatus(0);
            try {
                this.bookDao.insertBookItem(bookItem);
                result.put("msg", "图书入库成功,编号为:" + bookItem.getId());
            } catch (Exception e) {
                e.printStackTrace();
                result.put("msg", "图书入库出现异常");
                result.put("error", e.getMessage());
            }
        }
        return result;
    }

    @Override
    public Map<String, String> deleteBook(BookDeleteRequest request) {
        Map<String, String> result = new HashMap<>(10);
        if (request.getBookItemId() == null || "".equals(request.getBookItemId())) {
            result.put("msg", "不存在图书编码");
        }
        BookItem bookItem = this.bookDao.queryBook(request.getBookItemId());
        if (bookItem == null) {
            result.put("msg", "未入库的图书编码");
        } else {
            switch (bookItem.getStatus()) {
                case 0:
                    try {
                        this.bookDao.deleteBook(request.getBookItemId());
                        result.put("msg", "删除图书成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.put("msg", "删除图书失败");
                        result.put("error", e.getMessage());
                    }
                    break;
                case 1:
                    result.put("msg", "该图书还未归还，请先归还");
                    break;
                case 2:
                    result.put("msg", "该图书已被删除，请勿重复删除");
                    break;
            }
        }
        return result;
    }

    @Override
    public Map<String, String> returnBook(BookReturnRequest request) {
        Map<String, String> result = new HashMap<>(10);
        if (request.getBookItemId() == null || "".equals(request.getBookItemId())) {
            result.put("msg", "无效的图书编码");
        }
        BookItem bookItem = this.bookDao.queryBook(request.getBookItemId());
        if (bookItem == null) {
            result.put("msg", "该图书未入库");
        } else {
            switch (bookItem.getStatus()) {
                case 0:
                    result.put("msg", "该图书未处于租借状态，无需归还");
                    break;
                case 1:
                    try {
                        this.bookDao.returnBook(request.getBookItemId());
                        result.put("msg", "归还成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.put("msg", "归还异常");
                        result.put("error", e.getMessage());
                    }
                    break;
                case 2:
                    result.put("msg", "该图书已被删除");
                    break;
            }
        }
        return result;
    }
}
