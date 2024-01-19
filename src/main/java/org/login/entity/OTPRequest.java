package org.login.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTPRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long otp;
    private String email;
    private String phoneNumber;
}
