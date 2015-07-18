package com.lovely3x.jsonpareser.bean;

import com.lovely3x.jsonparser.annotations.JSON;
import com.lovely3x.jsonparser.annotations.JSONArray;
import com.lovely3x.jsonparser.annotations.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovely3x on 15-7-18.
 */
public class Person {

    /**
     * 此人的id
     */
    @JSON("id")
    private int id;
    /**
     * 名字
     */
    @JSON("name")
    private String name;

    /**
     * 性别
     */
    @JSON("sex")
    private int sex;

    /**
     * 好友
     */
    @JSONArray(value = "friends", container = ArrayList.class, object = Person.class)
    private List<Person> friends;

    /**
     * 伴侣
     */
    @JSONObject(value = "partner", object = Person.class)
    private Person partner;

    public Person() {
    }

    public Person(int id, String name, int sex, List<Person> friends, Person partner) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.friends = friends;
        this.partner = partner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    public Person getPartner() {
        return partner;
    }

    public void setPartner(Person partner) {
        this.partner = partner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (sex != person.sex) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (friends != null ? !friends.equals(person.friends) : person.friends != null) return false;
        return !(partner != null ? !partner.equals(person.partner) : person.partner != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + sex;
        result = 31 * result + (friends != null ? friends.hashCode() : 0);
        result = 31 * result + (partner != null ? partner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", friends=" + friends +
                ", partner=" + partner +
                '}';
    }
}
