package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by greenlucky on 6/3/17.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    @NotNull
    private double price;

    public Book() {
    }

    public Book(long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public static class BookBuilder {
        private long id;

        private String name;

        private String description;

        private double price;

        public BookBuilder() {
        }

        public BookBuilder setId(long id) {
            this.id = id;
            return  this;
        }

        public BookBuilder setName(String name) {
            this.name = name;
            return  this;
        }

        public BookBuilder setDescription(String description) {
            this.description = description;
            return  this;
        }

        public BookBuilder setPrice(double price) {
            this.price = price;
            return  this;
        }

        public Book createBook() {
            return new Book(id, name, description, price);
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id == book.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
