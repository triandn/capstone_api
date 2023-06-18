package com.example.capstone_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="tours")
@DynamicUpdate
public class Tour extends BaseEntity{
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tour_id", nullable = false)
    private Long tourId;

    @Column(name = "title")
    private String title;

    @Column(name = "rating", nullable = false)
    private Float rating;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "price_one_person", nullable = false)
    private Double priceOnePerson;

    @Column(name = "image_main", nullable = false)
    private String imageMain;

    @Column(name = "working", nullable = false)
    private String working;

    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @Column(name = "time_slot_length")
    private int timeSlotLength;

    @Column(name = "check_in")
    private Timestamp checkIn;

    @Column(name = "check_out")
    private Timestamp checkOut;

    @Column(name = "time_book_start")
    private LocalTime timeBookStart;

    @Column(name = "time_book_end")
    private LocalTime timeBookEnd;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "destination_description", nullable = false)
    private String destinationDescription;

    @Column(name="is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "user_id")
    private UUID userId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,insertable = false,updatable = false)
    private User user;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "tour_category", joinColumns = @JoinColumn(name = "tour_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ImageDetail> imageDetails = new ArrayList<>();
}
