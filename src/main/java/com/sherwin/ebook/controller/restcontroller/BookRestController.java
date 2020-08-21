package com.sherwin.ebook.controller.restcontroller;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookRestController {

	@Autowired
	private BookService bookService;

	@GetMapping("/bookrestdata")
	public List<Book> getAllBooks() {
		return bookService.findAll();
	}

	@GetMapping("/book/search/{ajxsearchcontent}")
	public List<Book> search(@PathVariable("ajxsearchcontent") String ajxsearchcontent){
		System.out.println(ajxsearchcontent);
		return  bookService.search(ajxsearchcontent);
	}

	@GetMapping("/ajaxsearch/book")
	public List<Book> showBook(Model model){
		List<Book> results = bookService.search("");
		model.addAttribute("Books",results);
//        return "Book/list :: table-refresh";
		return results;
	}

}
