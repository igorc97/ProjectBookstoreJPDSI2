package bookstore_project_ejbb.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orderbook database table.
 * 
 */
@Entity
@NamedQuery(name="Orderbook.findAll", query="SELECT o FROM Orderbook o")
public class Orderbook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOrderBook;

	private double price;

	//bi-directional many-to-one association to Book
	@ManyToOne
	private Book book;

	//bi-directional many-to-one association to Order
	@ManyToOne
	private Order order;

	public Orderbook() {
	}

	public int getIdOrderBook() {
		return this.idOrderBook;
	}

	public void setIdOrderBook(int idOrderBook) {
		this.idOrderBook = idOrderBook;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}