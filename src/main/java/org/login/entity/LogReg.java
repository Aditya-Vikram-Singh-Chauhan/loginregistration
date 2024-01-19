package org.login.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "LogReg")
public class LogReg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long contact_num;
    private String emailID;
    private String name;
    private String username;
    private String password;

}
