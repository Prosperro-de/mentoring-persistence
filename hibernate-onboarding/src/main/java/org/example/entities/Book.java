package org.example.entities;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Size(min=1, max=255, message = "name must be in range of (0;255)")
    private String name;

    private String genre;

    private String author;

    @Column(name = "is_Free")
    private boolean isFree;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "taken_time")
    private Date takenTime;

    @Min(value = 1)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @Min(value = 1, message = "Price must have positive value")
    private int price;
}
