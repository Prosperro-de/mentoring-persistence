package org.example.entities;

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
public class Customer implements Entity {

    @Id
    private Long id;

    private String first_name;

    private String last_name ;

    private Integer age ;

    private String email ;

    private String address ;

}
