package com.jaygibran.deliveryfood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@EqualsAndHashCode
@Data
@Entity
public class User {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private String email;

    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateCreated;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"), name = "user_group")
    private List<Group> groups;

    public boolean passwordDoesNotMatch(String password) {
        return !this.password.equals(password);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
    }
}
