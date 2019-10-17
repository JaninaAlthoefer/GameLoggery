package com.example.ladyrei.gameloggery;

/**
 * Created by Janina on 02/12/2016.
 */
enum Platform
{
    PS1, PS3, PS4, XBOX360;
}


public class GameID {
    private String id;
    private String gameTitle;
    private Platform platform;

    public GameID(String id, String gameTitle, String gamePlatform) {
        this.id = id;
        this.gameTitle = gameTitle;

        if (gamePlatform.equals("PS"))
        {   platform = Platform.PS1;        }
        else if (gamePlatform.equals("PS3"))
        {   platform = Platform.PS3;        }
        else if (gamePlatform.equals("PS4"))
        {   platform = Platform.PS4;        }
        else
        {   platform = Platform.XBOX360;    }

    }


    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }


}
