package org.example.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
public class Book implements Entity{
    @Id
    private int id;

    private String name;

    private String genre;

    private String author;

    private boolean is_Free;

    private long customer_id;

    private Date taken_time;

    private int quantity;

    private long library_id;

    private int price;
}
