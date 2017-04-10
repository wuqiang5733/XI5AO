package org.xuxiaoxiao.xiao.model;

/**
 *  Created by WuQiang on 2017/3/29.
 *  Copyright © 2017年 WuQiang. All rights reserved.
*/
public class ChatMessage {

    private String message;
    private String author;
    private String messageID;

    // Required default constructor for Wilddog object mapping
    @SuppressWarnings("unused")
    private ChatMessage() {
    }

    public ChatMessage(String message, String author, String messageid) {
        this.message = message;
        this.author = author;
        this.messageID = messageid;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
