package j2ee_project.model.user;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Forgotten password query
 */
@Entity
public class ForgottenPassword {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "idUser")
    private User user;
    @Basic
    @Column(name = "token", nullable = false, length = 50)
    private String token;
    @Basic
    @Column(name = "expiryDate", nullable = false)
    private LocalDateTime expiryDate;

    /**
     * Instantiates a new Forgotten password.
     *
     * @param user  the user
     * @param token the token
     */
    public ForgottenPassword(User user, String token) {
        this.user = user;
        this.token = token;
        //this.expiryDate = new Date(Date.from(Instant.now().plus(Duration.ofDays(1))).getTime());
        this.expiryDate = LocalDateTime.now().plusDays(1);
    }

    /**
     * Instantiates a new Forgotten password.
     */
    public ForgottenPassword() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets expiry date.
     *
     * @return the expiry date
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets expiry date.
     *
     * @param expiryDate the expiry date
     */
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForgottenPassword that = (ForgottenPassword) o;
        return id == that.id && Objects.equals(user, that.user) && Objects.equals(token, that.token) && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, token, expiryDate);
    }

    @Override
    public String toString() {
        return "ForgottenPassword{" +
                "id=" + id +
                ", token=" + token +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
