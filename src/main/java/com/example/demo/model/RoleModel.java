package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel extends BaseModel {

    private String name;

    public RoleModel(Long id) {
        this.setId(id);
    }

    public RoleModel(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }


}
