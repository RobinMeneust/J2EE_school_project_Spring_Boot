package j2ee_project.model;

import jakarta.persistence.*;

import java.sql.Date;

/**
 * Email that has been sent
 */
@Entity
public class Mail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "fromAddress", nullable = false, length = 50)
    private String fromAddress;
    @Basic
    @Column(name = "toAddress", nullable = false, length = 50)
    private String toAddress;
    @Basic
    @Column(name = "subject", nullable = false, length = 50)
    private String subject;
    @Basic
    @Column(name = "body", nullable = false, length = 300)
    private String body;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;

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
     * Gets source address.
     *
     * @return the source address
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * Sets source address.
     *
     * @param fromAddress the source address
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * Gets destination address.
     *
     * @return the destination address
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * Sets destination address.
     *
     * @param toAddress the destination address
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets expedition date.
     *
     * @return the expedition date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets expedition date.
     *
     * @param date the expedition date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mail mail = (Mail) o;

        if (id != mail.id) return false;
        if (fromAddress != null ? !fromAddress.equals(mail.fromAddress) : mail.fromAddress != null) return false;
        if (toAddress != null ? !toAddress.equals(mail.toAddress) : mail.toAddress != null) return false;
        if (subject != null ? !subject.equals(mail.subject) : mail.subject != null) return false;
        if (body != null ? !body.equals(mail.body) : mail.body != null) return false;
        if (date != null ? !date.equals(mail.date) : mail.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fromAddress != null ? fromAddress.hashCode() : 0);
        result = 31 * result + (toAddress != null ? toAddress.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
