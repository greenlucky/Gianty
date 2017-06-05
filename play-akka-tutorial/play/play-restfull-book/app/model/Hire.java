package model;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

/**
 * Created by greenlucky on 6/3/17.
 */
public class Hire {

    @MongoId
    @MongoObjectId
    private long id;

    private long hireDate;

    private long returnDate;

    private String forUser;


    private List<String> books;

    public Hire() {
    }

    public Hire(long id, long hireDate, long returnDate, String forUser, List<String> books) {
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

    public String getForUser() {
        return forUser;
    }

    public void setForUser(String forUser) {
        this.forUser = forUser;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public static class HireBuilder {

        private long id;

        private long hireDate;

        private long returnDate;

        private String forUser;

        private List<String> books;

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

        public HireBuilder setForUser(String forUser) {
            this.forUser = forUser;
            return this;
        }

        public HireBuilder setBooks(List<String> books) {
            this.books = books;
            return this;
        }

        public HireBuilder addBook(String book) {
            this.books.add(book);
            return this;
        }

        public Hire createBuilder() {
            return new Hire(id, hireDate, returnDate, forUser, books);
        }
    }
}
