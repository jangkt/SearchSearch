package com.server1.addserver1;

public class News {
    String subject;
    String content;
    public News(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
    public String getSubject() {
        return subject;
    }
    public String getContent() {
        return content;
    }
}
