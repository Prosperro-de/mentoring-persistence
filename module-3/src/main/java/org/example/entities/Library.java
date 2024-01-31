package org.example.entities;

import java.util.List;

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
@Table
public class Library implements Entity{
    @Id
    private int id;

    private String name;

    private String address ;

    private List<Customer> customers;

    private List<Book> books;
}
