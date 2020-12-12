package com.pinartekes.api.dao.entity;

import com.pinartekes.api.dao.entity.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users", indexes = {@Index(name = "idx_username", columnList = "username")},uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Long Id;


    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;
}
