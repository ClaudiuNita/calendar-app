package org.example.model;

import jakarta.persistence.*;

@Entity
public class Calendar_User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Calendar calendar;

    @ManyToOne
    private User user;
}
