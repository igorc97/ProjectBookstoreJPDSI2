package bookstore_project_ejbb.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name = "myorder")

@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOrder;

	@Temporal(TemporalType.DATE)
	private Date dateOfOrder;

	@Temporal(TemporalType.DATE)
	private Date dateOfReceive;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to Orderbook
	@OneToMany(mappedBy="order")
	private List<Orderbook> orderbooks;

	//public Order() {
	//}

	public int getIdOrder() {
		return this.idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public Date getDateOfOrder() {
		return this.dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public Date getDateOfReceive() {
		return this.dateOfReceive;
	}

	public void setDateOfReceive(Date dateOfReceive) {
		this.dateOfReceive = dateOfReceive;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Orderbook> getOrderbooks() {
		return this.orderbooks;
	}

	public void setOrderbooks(List<Orderbook> orderbooks) {
		this.orderbooks = orderbooks;
	}

	public Orderbook addOrderbook(Orderbook orderbook) {
		getOrderbooks().add(orderbook);
		orderbook.setOrder(this);

		return orderbook;
	}

	public Orderbook removeOrderbook(Orderbook orderbook) {
		getOrderbooks().remove(orderbook);
		orderbook.setOrder(null);

		return orderbook;
	}

}