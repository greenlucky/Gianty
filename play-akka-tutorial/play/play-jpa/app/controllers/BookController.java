package controllers;

import models.Book;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.BookRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

/**
 * Created by greenlucky on 6/3/17.
 */
public class BookController extends Controller {

    private final FormFactory formFactory;
    private final BookRepository bookRepository;
    private final HttpExecutionContext ec;

    @Inject
    public BookController(FormFactory formFactory, BookRepository bookRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.bookRepository = bookRepository;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.book.render());
    }

    public CompletionStage<Result> addBook() {
        Book book = formFactory.form(Book.class).bindFromRequest().get();
        return bookRepository.add(book).thenApplyAsync(b -> {
            return redirect(routes.BookController.index());
        }, ec.current());
    }

    public CompletionStage<Result> deleteBook(long id) {
        return bookRepository.delete(id).thenApplyAsync(b -> {
            return redirect(routes.BookController.index());
        }, ec.current());
    }

    public CompletionStage<Result> getBooks() {
        return bookRepository.list().thenApplyAsync(b -> {
            return ok(toJson(b.collect(Collectors.toList())));
        }, ec.current());
    }
}
