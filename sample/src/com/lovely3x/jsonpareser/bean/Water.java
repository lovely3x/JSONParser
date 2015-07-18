package com.lovely3x.jsonpareser.bean;

public class Water {
    private String wind;

    private String dayPictureUrl;

    private String weather;

    private String nightPictureUrl;

    private String date;

    private String temperature;


    public Water() {
    }

    public Water(String wind, String dayPictureUrl, String weather, String nightPictureUrl, String date, String temperature) {
        this.wind = wind;
        this.dayPictureUrl = dayPictureUrl;
        this.weather = weather;
        this.nightPictureUrl = nightPictureUrl;
        this.date = date;
        this.temperature = temperature;
    }

    public String getWind() {
        return this.wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDayPictureUrl() {
        return this.dayPictureUrl;
    }

    public void setDayPictureUrl(String dayPictureUrl) {
        this.dayPictureUrl = dayPictureUrl;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getNightPictureUrl() {
        return this.nightPictureUrl;
    }

    public void setNightPictureUrl(String nightPictureUrl) {
        this.nightPictureUrl = nightPictureUrl;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String toString() {
        return new StringBuilder()
                .append("Water = { ")
                .append("wind").append(" = ").append(wind)
                .append(',').append("dayPictureUrl").append(" = ").append(dayPictureUrl)
                .append(',').append("weather").append(" = ").append(weather)
                .append(',').append("nightPictureUrl").append(" = ").append(nightPictureUrl)
                .append(',').append("date").append(" = ").append(date)
                .append(',').append("temperature").append(" = ").append(temperature)
                .append('}').toString();
    }
}