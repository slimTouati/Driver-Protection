package com.example.tanga.driverprotection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    @SerializedName("_id")
    private int id;


    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("photo")
    private String photo;
    @SerializedName("vehicle")
    private int vehicle;

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    /*@SerializedName("Prefrences")
    private List<Cause> prefrences = new ArrayList<>();
    @SerializedName("Users")
    private List<User> following = new ArrayList<>();
    @SerializedName("Subs")
    private List<User> followers = new ArrayList<>();
    @SerializedName("Favourites")
    private List<Event> favs = new ArrayList<>();
    @SerializedName("Events")
    private List<Event> events = new ArrayList<>();
    @SerializedName("Posts")
    private List<Post> posts = new ArrayList<>();*/



    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   /* public List<Cause> getPrefrences() {
        return prefrences;
    }

    public void setPrefrences(ArrayList<Cause> prefrences) {
        this.prefrences = prefrences;
    }
*/


   /* public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getrole() {
        return role;
    }

    public void setrole(String role) {
        this.role = role;
    }

    public String getSocialPlatform() {
        return socialPlatform;
    }

    public void setSocialPlatform(String socialPlatform) {
        this.socialPlatform = socialPlatform;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

  /*  public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getConfirmationPhoto() {
        return confirmationPhoto;
    }

    public void setConfirmationPhoto(String confirmationPhoto) {
        this.confirmationPhoto = confirmationPhoto;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
*/
  /*  public void setPrefrences(List<Cause> prefrences) {
        this.prefrences = prefrences;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<Event> getEvents() {
        return events;
    }



    public List<Event> getFavs() {
        return favs;
    }

    public void setFavs(List<Event> favs) {
        this.favs = favs;
    }


    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }*/

}
