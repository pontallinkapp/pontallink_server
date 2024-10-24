package com.pontallink_server.pontallink.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "interests")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "interest", nullable = false)
    private String interest;

    // Construtor vazio (necessário para JPA)
    public Interest() {
    }

    // Construtor com parâmetros
    public Interest(User user, String interest) {
        this.user = user;
        this.interest = interest;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
