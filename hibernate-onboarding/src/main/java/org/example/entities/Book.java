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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    private Long id;

    @Size(min=1, max=255, message = "name must be in range of (0;255)")
    private String name;

    private String genre;

    private String author;

    @Column(name = "is_Free")
    private Boolean isFree;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "taken_time")
    private Date takenTime;

    @Min(value = 1)
    private Integer quantity;

    //TODO зробити каскад(видалялось/не видалялось) Cascade type

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @Min(value = 1, message = "Price must have positive value")
    private Integer price;
}
