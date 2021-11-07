package com.ldg.cloud.equals;

import java.io.Serializable;

public class User2 implements Serializable {
    private Integer id;
    private String name;
    private String password;

    public User2(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User2(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User2)) return false;

        User2 user2 = (User2) o;

        return new org.apache.commons.lang.builder.EqualsBuilder().append(id, user2.id).append(name, user2.name).append(password, user2.password).isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang.builder.HashCodeBuilder(17, 37).append(id).append(name).append(password).toHashCode();
    }
}
