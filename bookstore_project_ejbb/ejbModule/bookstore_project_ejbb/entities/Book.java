package bookstore_project_ejbb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBook;

	@Lob
	private String author;

	private byte availability;

	@Lob
	private String description;

	@Lob
	private String isbn;

	private double price;

	@Lob
	private String publishingHouse;

	@Temporal(TemporalType.DATE)
	private Date releaseDate;

	@Lob
	private String title;

	//bi-directional many-to-one association to Orderbook
	@OneToMany(mappedBy="book")
	private List<Orderbook> orderbooks;

	public Book() {
	}

	public int getIdBook() {
		return this.idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public byte getAvailability() {
		return this.availability;
	}

	public void setAvailability(byte availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPublishingHouse() {
		return this.publishingHouse;
	}

	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Orderbook> getOrderbooks() {
		return this.orderbooks;
	}

	public void setOrderbooks(List<Orderbook> orderbooks) {
		this.orderbooks = orderbooks;
	}

	public Orderbook addOrderbook(Orderbook orderbook) {
		getOrderbooks().add(orderbook);
		orderbook.setBook(this);

		return orderbook;
	}

	public Orderbook removeOrderbook(Orderbook orderbook) {
		getOrderbooks().remove(orderbook);
		orderbook.setBook(null);

		return orderbook;
	}

}