package com.dx.expense.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_description")
    private String roleDescription;

    public enum Types {
        ADMIN("ADMIN"),
        BASIC("BASIC");

        private final String roleDescription;

        Types(String roleDescription) {
            this.roleDescription = roleDescription;
        }

        public String getRoleDescription() {
            return roleDescription;
        }
    }

}
