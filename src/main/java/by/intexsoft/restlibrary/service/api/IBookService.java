package by.intexsoft.restlibrary.service.api;

import by.intexsoft.restlibrary.exception.ServiceException;
import by.intexsoft.restlibrary.model.Book;
import by.intexsoft.restlibrary.model.dto.BookDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface IBookService extends ICrudService<Book, Long> {

    Long uploadExcelFile(MultipartFile file) throws ServiceException;

    void saveOrUpdateAll(Set<Book> books) throws ServiceException;

    List<BookDTO> getAllBooksDTO();
}
