package com.example.reading_list.controller;

import com.example.reading_list.domain.Book;
import com.example.reading_list.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller // for component scanning to pick it and register it as a bean in the app
@RequestMapping("/") // to handle all base urls "/"
public class ReadingListController {
    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    /**
     * GET request for retrieving a Book list from the repository injected
     * The retrieved Book list is put into a model under the key "books"
     * It then returns "readingList" as the logical name of the view to render the model
     * */
    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readerBooks(@PathVariable("reader") String reader, Model model){
        List<Book> readingList = readingListRepository.findByReader(reader);

        if (readingList != null){
            model.addAttribute("books", readingList);
        }

        return "readingList";
    }

    /**
     * POST request for binding the data in request body to a Book object
     * Sets the object's reader property ro the reader's name
     * Saves the modified Book via repository's save() method
     * Returns redirect to "/{reader} - to be handled by another controller"
     * */
    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect: /{reader}";
    }
}
