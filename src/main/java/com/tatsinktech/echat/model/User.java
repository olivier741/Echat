package com.tatsinktech.echat.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * User entity class
 */
@Entity
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User extends AbstractModel<Long> {

    private String name;
    private String password;

    private Date loginDate;
    private Date logoutDate;

}
