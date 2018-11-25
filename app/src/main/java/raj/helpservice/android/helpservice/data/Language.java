package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 */

public class Language {

    private static final String SELECTED_YES = "Y";

    @SerializedName("languageId")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("selected")
    public String selected;

    public Language(int id, String name, String selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public boolean isSelected() {
        if (selected == null || !selected.equalsIgnoreCase(SELECTED_YES)) {
            return false;
        }
        return true;
    }

    public void setSelected(boolean isSelected) {
        if (isSelected) {
            selected = SELECTED_YES;
        } else {
            selected = "N";
        }
    }
}
