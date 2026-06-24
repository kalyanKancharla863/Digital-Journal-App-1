package com.digital_journal_app.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @NotBlank
    @Size(min = 3 , max = 50)
    private String userName;

    @NotBlank
    @Email
    @Size(max=50)
    private String email;

    @NotBlank
    @Size(min = 3, max=500)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<JournalEntry> journalEntries;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn( name = "role_id" )
    )
    private Set<Role> roles = new HashSet<>();


    public User( String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
