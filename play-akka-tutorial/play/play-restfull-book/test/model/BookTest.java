package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lam.nm on 6/24/2017.
 */
public class BookTest {

    @Before
    public void init() throws Exception {

    }

    @Test
    public void addBookTest() throws  Exception {

        Book book = new Book.BookBuilder().setName("Java Beginning").createBuilder();

        Assert.assertEquals("Java Beginning", book.getName());

    }
}