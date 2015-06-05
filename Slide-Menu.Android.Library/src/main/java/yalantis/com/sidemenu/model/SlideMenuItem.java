package yalantis.com.sidemenu.model;

import yalantis.com.sidemenu.interfaces.Resourceble;

/**
 * Created by Konstantin on 23.12.2014.
 */
public class SlideMenuItem implements Resourceble {
    private String name;
    private int imageRes;
    private int backgroundDrawableRes;

    public SlideMenuItem(String name, int imageRes, int backgroundDrawableRes) {
        this.name = name;
        this.imageRes = imageRes;
        this.backgroundDrawableRes = backgroundDrawableRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    @Override
    public int getBackgroundDrawableRes() {
        return backgroundDrawableRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public void setBackgroundDrawableRes(int backgroundDrawableRes) {
        this.backgroundDrawableRes = backgroundDrawableRes;
    }
}
