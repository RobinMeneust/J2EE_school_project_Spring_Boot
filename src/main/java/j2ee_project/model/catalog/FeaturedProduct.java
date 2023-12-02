package j2ee_project.model.catalog;

import jakarta.persistence.*;

@Entity
@Table(name="`FeaturedProduct`")
@PrimaryKeyJoinColumn(name = "`idProduct`")
public class FeaturedProduct extends Product { }
