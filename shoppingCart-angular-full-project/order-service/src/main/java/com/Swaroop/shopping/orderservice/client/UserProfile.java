package com.Swaroop.shopping.orderservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserProfile {
    @Id
    private String _id;

    private int profileId;
    private String fullName;
    private String image;
    private String email;
    private long mobileNumber;
    private String about;
    private LocalDate dateOfBirth;
    private String gender;
    private String role;
    private String password;
    private Boolean isAdmin;

    private String address;
}
