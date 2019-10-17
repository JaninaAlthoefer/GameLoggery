package com.example.ladyrei.gameloggery;

/**
 * Created by Janina on 01/12/2016.
 */

public class GameItem {

    private String name;
    private String imgUrl;
    private String charUrl;
    private String publisher;
    private Platform platform;
    private String developer;

    private int year;
    private int hours;
    private int rating;

    private boolean beaten;

    public boolean isBeaten() {
        return beaten;
    }

    public void setBeaten(boolean beaten) {
        this.beaten = beaten;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCharUrl() {
        return charUrl;
    }

    public void setCharUrl(String charUrl) {
        this.charUrl = charUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) { this.rating = rating; }

   public GameItem(String name, String imgUrl, String charUrl, String publisher, String gamePlatform, String developer, String year, String hours, String rating, String beaten) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.charUrl = charUrl;
        this.publisher = publisher;
        this.developer = developer;

        this.year = Integer.parseInt(year);
        this.hours = Integer.parseInt(hours);
        this.rating = Integer.parseInt(rating);

       if (beaten.equals("yes"))
           this.beaten = true;
       else
           this.beaten = false;

       if (gamePlatform.equals("Playstation"))
       {   this.platform = Platform.PS1;        }
       else if (gamePlatform.equals("Playstation 3"))
       {   this.platform = Platform.PS3;        }
       else if (gamePlatform.equals("Playstation 4"))
       {   this.platform = Platform.PS4;        }
       else
       {   this.platform = Platform.XBOX360;    }


   }
}
