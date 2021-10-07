package com.example.demo.entity;

import com.example.demo.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "user_name")
    private String userName;
    private String email;
    @Column(columnDefinition = "varchar(128)")
    private String password;
    @Column(columnDefinition = "varchar(128) default 'avatar' ")
    private String avatar;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "role_id", columnDefinition = "bigint")
    private Role role;
    @Column(name = "active", columnDefinition = "boolean")
    private boolean active = false;

    public static UserModel convertToModel(User user, boolean ignoreField) {
        ModelMapper modelMapper = new ModelMapper();
        UserModel userModel = modelMapper.map(user, UserModel.class);
        userModel.setPassword(null);
        if (ignoreField)
            userModel = UserModel.ignoreField(userModel);
        return userModel;
    }

    public void update(UserModel userModel) {
        this.setAvatar(userModel.getAvatar());
        this.setFullName(userModel.getFullName());
        this.setPhoneNumber(userModel.getPhoneNumber());
    }

}
