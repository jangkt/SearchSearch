package com.server1.addserver1;

import java.util.ArrayList;

public class getServer {
    String server;
    String category;
    getPHP php;

    public getServer(String server, String category) {
        this.server = server;
        this.category = category;
        php = new getPHP();
        getserv(php);
    }

    public void getserv(getPHP php) {
        php.execute(server + category);
    }

    public ArrayList<News> getList() {
        return php.getList();
    }
    public void addServer(ArrayList<News> list){
        list.addAll(getList());
    }
}
