package org.example;

import lombok.Builder;
import lombok.Data;

/*
 {
    "id": 1,
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
    "address": {
      "street": "Kulas Light",
      "suite": "Apt. 556",
      "city": "Gwenborough",
      "zipcode": "92998-3874",
      "geo": {
        "lat": "-37.3159",
        "lng": "81.1496"
      }
    },
    "phone": "1-770-736-8031 x56442",
    "website": "hildegard.org",
    "company": {
      "name": "Romaguera-Crona",
      "catchPhrase": "Multi-layered client-server neural-net",
      "bs": "harness real-time e-markets"
    }
  }
  */
@Data
@Builder
class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private Geo geo;
    private String phone;
    private String website;
    private Company company;



}
@Data
class Geo {
    public String lat;
    public String lng;

    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
@Data
class Address {
    public String street;
    public String suite;
    public String city;
    public String zipcode;
    public Geo geo;

    public Address(String street, String suite, String city, String zipcode, Geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }
}
class Company{
    public String name;
    public String catchPhrase;
    public String bs;

    public Company(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }
}

@Data
@Builder
class Post {
    private Long postId;
    private Long id;
    private String name;
    private String email;
    private String body;

    public Post(Long postId, Long id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }
}

