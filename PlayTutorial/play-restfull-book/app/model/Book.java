package model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by greenlucky on 6/3/17.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    private String description;

    private String author;

    @ManyToOne
    private BookType bookType;


    public Book() {
    }

    public Book(String name, String description, String author, BookType bookType) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.bookType = bookType;
    }
    public Book(long id, String name, String description, String author, BookType bookType) {
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

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public static class BookBuilder {
        private long id;

        private String name;

        private String description;

        private String author;

        private BookType bookType;

        public BookBuilder() {
        }

        public BookBuilder(String name, String description, String author, BookType bookType) {
            this.name = name;
            this.description = description;
            this.author = author;
            this.bookType = bookType;
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

        public BookBuilder setBookType(BookType bookType) {
            this.bookType = bookType;
            return this;
        }

        public Book createBuilder() {return new Book(name, description, author, bookType);}
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
