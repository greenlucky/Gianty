package model;


import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by greenlucky on 6/3/17.
 */
public class Book {

    @MongoId
    @MongoObjectId
    private long id;

    private String name;

    private String description;

    private String author;

    private String bookType;


    public Book() {
    }

    public Book(long id, String name, String description, String author, String bookType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.bookType = bookType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public static class BookBuilder {
        private long id;

        private String name;

        private String description;

        private String author;

        private String bookType;

        public BookBuilder() {
        }

        public BookBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public BookBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public BookBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public BookBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder setBookType(String bookType) {
            this.bookType = bookType;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", bookType=" + bookType +
                '}';
    }
}
