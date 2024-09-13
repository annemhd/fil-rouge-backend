package com.filrougeapp.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

// annotation pour indiquer que cette classe est une entité JPA mappée à une table dans la base de données
@Entity

// annotation pour spécifier le nom de la table dans la base de données associée à cette entité
@Table(name = "user")
public class User implements UserDetails {

    // annotation pour spécifier que cet attribut est la clé primaire de l'entité
    @Id

    // annotation pour spécifier que la valeur de la clé primaire sera générée automatiquement par la base de données
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // annotation pour définir le nom de la colonne dans la table de la base de données
    @Column(name = "id")
    Integer id;

    @Column(name = "firstname")
    String firstname;

    @Column(name = "lastname")
    String lastname;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    // annotation pour indiquer que l'attribut est un enum stocké en tant que chaîne dans la base de données
    @Enumerated(value = EnumType.STRING)
    Role role;

    // annotation pour définir une relation OneToMany avec l'entité Race, avec suppression en cascade et récupération paresseuse
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)

    // annotation pour gérer la sérialisation JSON, évitant les boucles infinies dans les relations bidirectionnelles
    @JsonManagedReference
    private List<Race> races;

    @Embedded
    private Avatar avatar;

    // méthode getter pour l'ID de l'utilisateur
    public Integer getId() {
        return id;
    }

    // méthode setter pour l'ID de l'utilisateur
    public void setId(Integer id) {
        this.id = id;
    }

    // méthode getter pour le prénom de l'utilisateur
    public String getFirstname() {
        return firstname;
    }

    // méthode setter pour le prénom de l'utilisateur
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    // méthode getter pour le nom de famille de l'utilisateur
    public String getLastname() {
        return lastname;
    }

    // méthode setter pour le nom de famille de l'utilisateur
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    // méthode getter pour le nom d'utilisateur
    public String getUsername() {
        return username;
    }

    // méthode setter pour le nom d'utilisateur
    public void setUsername(String username) {
        this.username = username;
    }

    // méthode getter pour le mot de passe
    public String getPassword() {
        return password;
    }

    // méthode setter pour le mot de passe
    public void setPassword(String password) {
        this.password = password;
    }

    // méthode getter pour le rôle de l'utilisateur
    public Role getRole() {
        return role;
    }

    // méthode setter pour le rôle de l'utilisateur
    public void setRole(Role role) {
        this.role = role;
        System.out.println("User role model : " + this.role);
    }

    // méthode getter pour la liste des courses associées à l'utilisateur
    public List<Race> getRaces() {
        return races;
    }

    // méthode setter pour la liste des courses associées à l'utilisateur
    public void setRaces(List<Race> races) {
        this.races = races;
    }

    // méthode pour obtenir les autorités/grants de l'utilisateur (pour Spring Security)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // méthode pour vérifier si le compte n'est pas expiré (toujours vrai ici)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // méthode pour vérifier si le compte n'est pas verrouillé (toujours vrai ici)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // méthode pour vérifier si les informations d'identification ne sont pas expirées (toujours vrai ici)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // méthode pour vérifier si le compte est activé (toujours vrai ici)
    @Override
    public boolean isEnabled() {
        return true;
    }

    //   public Avatar getAvatar() {
    public void setAvatar(Avatar avatar2) {
        throw new UnsupportedOperationException("Unimplemented method 'setAvatar'");
    }
}