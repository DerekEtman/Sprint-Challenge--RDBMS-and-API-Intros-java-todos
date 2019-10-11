package com.lambdaschool.todo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
public class Todo extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;

    @Column(nullable = false)
    private String description;

    protected Date datestarted;

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties("user")
    private User user;

    public Todo() {
    }

    public Todo(String description, Date datestarted, boolean completed, User user) {
        this.description = description;
        this.datestarted = datestarted;
        this.completed = completed;
        this.user = user;
    }

    public long getTodoid() {
        return todoid;
    }

    public void setTodoid(long todoid) {
        this.todoid = todoid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatestarted() {
        return datestarted;
    }

    public void setDatestarted(Date datestarted) {
        this.datestarted = datestarted;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
