package by.intexsoft.restlibrary.controller;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.dto.BookDTO;
import by.intexsoft.restlibrary.model.filter.BookFilter;
import by.intexsoft.restlibrary.model.response.MultiResponseList;
import by.intexsoft.restlibrary.service.api.IBookService;
import by.intexsoft.restlibrary.service.api.ILocalizationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(required = false) String lang) {
        try {
            return String.format(localeService.getString("ldb", lang), bookService.uploadFile(file));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return localeService.getString("bwnl", lang) + " " + localeService.getString("sww", lang);
        }
    }

    @GetMapping
    public MultiResponseList<BookDTO> getBooks(BookFilter filter, @RequestParam(required = false) String lang) {
        try {
            return new MultiResponseList<>(bookService.getAllBooksDTO(filter));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new MultiResponseList<>(localeService.getString("bwns", lang) + " " + localeService.getString("sww", lang));
        }
    }

    @GetMapping(value = "/download")
    public ResponseEntity<Object> downloadFile(BookFilter filter, @RequestParam(required = false) String lang) {
        try {
            List<BookDTO> books = bookService.getAllBooksDTO(filter);
            InputStreamResource resource = bookService.convertToCSV(books);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "books.csv")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(localeService.getString("sww", lang), HttpStatus.BAD_REQUEST);
        }
    }
}
