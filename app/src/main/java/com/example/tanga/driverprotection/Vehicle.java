package com.example.tanga.driverprotection;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vehicle implements Serializable {

    @SerializedName("_id")
    private int id;


    @SerializedName("model")
    private String model;
    @SerializedName("brand")
    private String brand;
    @SerializedName("color")
    private Integer color;
    @SerializedName("plates")
    private String plates;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public String getPlates() {
        return plates;
    }

    public void setPlates(String plates) {
        this.plates = plates;
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



    public Vehicle() {
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
