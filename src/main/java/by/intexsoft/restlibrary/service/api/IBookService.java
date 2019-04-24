package by.intexsoft.restlibrary.service.api;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.dto.BookDTO;
import by.intexsoft.restlibrary.model.filter.BookFilter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBookService extends ICrudService<Book, Long> {

    Long uploadExcelFile(MultipartFile file) throws ServiceException;

    List<BookDTO> getAllBooksDTO(BookFilter bookFilter) throws ServiceException;
}
