package j2ee_project.model.catalog;

import jakarta.persistence.*;

/**
 * Product featured on the hoem page
 */
@Entity
@PrimaryKeyJoinColumn(name = "idProduct")
public class FeaturedProduct extends Product { }
