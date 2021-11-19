package com.mycompany.slack.cryptoverse;

import java.io.IOException;
import org.json.JSONObject;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {

        JSONObject json = new JSONObject();

        json.put("text", "Slack funcionando time. Faz o Pix pro pai.");

        Slack.sendMessage(json);
    }
}
