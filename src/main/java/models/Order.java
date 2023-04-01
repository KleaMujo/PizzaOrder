package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "po_order")
@Getter @Setter @ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "date_created")
    protected Instant dateCreated;

    @Column(name = "total")
    protected double total;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="order", fetch = FetchType.EAGER)
    protected List<OrderItem> items;
}