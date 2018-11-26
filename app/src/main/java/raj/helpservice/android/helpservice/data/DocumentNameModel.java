package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 *
 */

public class DocumentNameModel {

    private static final String SELECTED_YES = "Y";

    @SerializedName("documentID")
    public int documentID;
    @SerializedName("name")
    public String name;
    @SerializedName("selected")
    private String selected;

    public boolean isSelected() {
        if (selected == null || !selected.equalsIgnoreCase(SELECTED_YES)) {
            return false;
        }
        return true;
    }
}
