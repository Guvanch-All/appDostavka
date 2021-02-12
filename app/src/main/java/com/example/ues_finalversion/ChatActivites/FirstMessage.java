package com.example.ues_finalversion.ChatActivites;

public class FirstMessage {

    //БУДЕМ ИСПОЛЬЗОВАТЬ ЭТИ ПОЛЯ КОГДА СОЗДАДИМ бд В FIREBASE
    private String text;
    private String Name;
    private String imageURL;
    private String sender;
    private String recipient;
    private  boolean isMine;


    public FirstMessage(String text, String name, String imageURL, String sender, String recipient, boolean isMine)
    {
        this.text = text;
        Name = name;
        this.imageURL = imageURL;
        this.sender = sender;
        this.recipient = recipient;
        this.isMine = isMine;
    }

    public FirstMessage() {

    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
