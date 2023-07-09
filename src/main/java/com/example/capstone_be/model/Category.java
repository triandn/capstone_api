package com.example.capstone_be.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.boot.model.source.spi.FetchCharacteristics;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="categories")
public class Category extends BaseEntity{
    @Id
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "image_link", nullable = false)
    private String imageLink;

    @ManyToMany(mappedBy = "categories",fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Tour> tours;

    public Category(int i, String s, String image) {
        super();
    }
}
