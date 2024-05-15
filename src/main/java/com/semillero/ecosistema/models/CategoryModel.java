package com.semillero.ecosistema.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category;

    @Column
    @Length(max =500)
    private String image;
    public CategoryModel(String category){
        this.category = category;
    }


}
