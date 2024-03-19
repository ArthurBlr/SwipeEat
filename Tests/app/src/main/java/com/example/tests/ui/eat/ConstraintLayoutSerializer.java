package com.example.tests.ui.eat;

import android.content.Context;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import org.json.JSONException;
import org.json.JSONObject;

public class ConstraintLayoutSerializer {

    // Serialize ConstraintLayout to JSON
    public static String serializeConstraintLayout(ConstraintLayout layout) {
        JSONObject jsonObject = new JSONObject();
        try {
            // Store layout parameters
            jsonObject.put("layout_width", layout.getLayoutParams().width);
            jsonObject.put("layout_height", layout.getLayoutParams().height);
            jsonObject.put("background_color", layout.getDrawingCacheBackgroundColor());

            // Add more properties as needed

            // Example: Store child count
            jsonObject.put("child_count", layout.getChildCount());

            // Store children properties
            for (int i = 0; i < layout.getChildCount(); i++) {
                JSONObject childObject = new JSONObject();
                // Example: Store child ID
                childObject.put("child_id", layout.getChildAt(i).getId());
                // Add more properties as needed

                // Store child properties in the main layout JSON object
                jsonObject.put("child_" + i, childObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    // Deserialize JSON to ConstraintLayout
    public static ConstraintLayout deserializeConstraintLayout(String jsonString, Context context) {
        ConstraintLayout layout = new ConstraintLayout(context);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            // Restore layout parameters
            int layoutWidth = jsonObject.getInt("layout_width");
            int layoutHeight = jsonObject.getInt("layout_height");
            int backgroundColor = jsonObject.getInt("background_color");
            layout.setLayoutParams(new ConstraintLayout.LayoutParams(layoutWidth, layoutHeight));
            layout.setBackgroundColor(backgroundColor);

            // Restore children
            int childCount = jsonObject.getInt("child_count");
            for (int i = 0; i < childCount; i++) {
                JSONObject childObject = jsonObject.getJSONObject("child_" + i);

                // Example: Restore child ID
                int childId = childObject.getInt("child_id");

                // Create child view and add it to the layout
                // Note: You need to implement this part based on your child view types
                // For simplicity, let's assume all children are buttons
                Button childButton = new Button(context);
                childButton.setId(childId);
                // Add more properties as needed
                // ...

                layout.addView(childButton);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return layout;
    }
}
