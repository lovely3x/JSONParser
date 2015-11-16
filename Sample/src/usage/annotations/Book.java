package usage.annotations;

import com.lovely3x.jsonparser.annotations.JSON;

public class Book {

    @JSON("book_author")
    private String author;

    @JSON("book_price")
    private float price;

    @JSON("book_name")
    private String name;

    @JSON("book_id")
    private int id;

    @JSON("book_publish_date")
    private long publishDate;


    public Book() {
    }

    public Book(String author, float price, String name, long publishDate, int id) {
        this.author = author;
        this.price = price;
        this.name = name;
        this.publishDate = publishDate;
        this.id = id;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return new StringBuilder()
                .append("Book = { ")
                .append("author").append(" = ").append(author)
                .append(',').append("price").append(" = ").append(price)
                .append(',').append("name").append(" = ").append(name)
                .append(',').append("publishDate").append(" = ").append(publishDate)
                .append(',').append("id").append(" = ").append(id)
                .append('}').toString();
    }
}