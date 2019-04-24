package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.dto.BookDTO;
import by.intexsoft.restlibrary.model.filter.BookFilter;
import by.intexsoft.restlibrary.model.response.MultiResponseList;
import by.intexsoft.restlibrary.service.api.IBookService;
import by.intexsoft.restlibrary.service.api.ILocalizationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {
    private static final Logger logger = Logger.getLogger(BookController.class);
    private final IBookService bookService;
    private final ILocalizationService localeService;

    @Autowired
    public BookController(IBookService bookService, ILocalizationService localeService) {
        this.bookService = bookService;
        this.localeService = localeService;
    }

    @PostMapping(value = "/uploadExcelFile")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, @RequestParam(required = false) String lang) {
        try {
            return String.format(localeService.getString("ldb", lang), bookService.uploadExcelFile(file));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return localeService.getString("bwnl", lang) + " " + localeService.getString("sww", lang);
        }
    }

    @GetMapping
    public MultiResponseList<BookDTO> getBooks(@RequestParam Map<String, String> parameters) {
        BookFilter bookFilter = BookFilter.builder()
                .filterByName(parameters.get("name"))
                .filterByGenre(parameters.get("genre"))
                .filterByStartDate(parameters.get("from"))
                .filterByEndDate(parameters.get("to"))
                .build();
        try {
            return new MultiResponseList<>(bookService.getAllBooksDTO(bookFilter));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>(e.getMessage());
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>(localeService.getString("bwns", parameters.get("lang")) + " " + localeService.getString("iid", parameters.get("lang")));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>(localeService.getString("bwns", parameters.get("lang")) + " " + localeService.getString("sww", parameters.get("lang")));
        }
    }
}
