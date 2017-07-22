package model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by greenlucky on 6/3/17.
 */
@Entity
public class Hire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long hireDate;

    private long returnDate;

    @ManyToOne
    private User forUser;

    @OneToMany
    private List<Book> books;

    public Hire() {
    }

    public Hire(long id, long hireDate, long returnDate, User forUser, List<Book> books) {
        this.id = id;
        this.hireDate = hireDate;
        this.returnDate = returnDate;
        this.forUser = forUser;
        this.books = books;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHireDate() {
        return hireDate;
    }

    public void setHireDate(long hireDate) {
        this.hireDate = hireDate;
    }

    public long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(long returnDate) {
        this.returnDate = returnDate;
    }

    public User getForUser() {
        return forUser;
    }

    public void setForUser(User forUser) {
        this.forUser = forUser;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public static class HireBuilder {

        private long id;

        private long hireDate;

        private long returnDate;

        private User forUser;

        private List<Book> books;

        public HireBuilder() {
        }

        public HireBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public HireBuilder setHireDate(long hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public HireBuilder setReturnDate(long returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public HireBuilder setForUser(User forUser) {
            this.forUser = forUser;
            return this;
        }

        public HireBuilder setBooks(List<Book> books) {
            this.books = books;
            return this;
        }

        public HireBuilder addBook(Book book) {
            this.books.add(book);
            return this;
        }

        public Hire createBuilder() {
            return new Hire(id, hireDate, returnDate, forUser, books);
        }
    }
}
