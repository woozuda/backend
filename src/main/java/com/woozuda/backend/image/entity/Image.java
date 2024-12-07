package com.woozuda.backend.image.entity;


import com.woozuda.backend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="image")
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_linked_to_post")
    private Boolean isLinkedToPost;

    public Image(String imageUrl, Boolean isLinkedToPost) {
        this.imageUrl = imageUrl;
        this.isLinkedToPost = isLinkedToPost;
    }

    public static Image of(String imageUrl, Boolean isLinkedToPost) {
        return new Image(imageUrl, isLinkedToPost);
    }
}
