package com.example.pricetracker;

import com.codename1.system.Lifecycle;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;


/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose
 * of building native mobile applications using Java.
 */
public class PriceTracker extends Lifecycle {
    @Override
    public void runApp() {
        Form hi = new Form("Hi World", BoxLayout.y());
        Button helloButton = new Button("Show Product Info");
        hi.add(helloButton);
        helloButton.addActionListener(e -> hello());
        hi.getToolbar().addMaterialCommandToSideMenu("Hello Command", FontImage.MATERIAL_CHECK, 4, e -> hello());
        hi.show();
    }
    
    private void hello() {
        Product p = new Product("https://www.amazon.com/AmazonBasics-Matte-Keyboard-QWERTY-Layout/dp/B07WJ5D3H4/ref=sr_1_5?crid=1HNR5G8ZU9BQV&dib=eyJ2IjoiMSJ9.iQx-lJra_ejVJqdnNACdKZng8aRk-GUZrm3hrHbhIIxBSHhUS0KbTzuPYO3rSMlwd45bBxfIui-lZeD2-DRI6AC162oc8ZJtiWiXQCw038SXeOgw5jyxyZisstdrZlTUrXhH2WQla9Q03zcpxT-EiCVy80fxSLqBvNCkL70ff_yYOHwWa8grBIhdT2jWeDgK1dN3FAsbGS27HJlo1twGOFqO3WKtm6PUw5fWgcXydKg.bu6kFOcXK9l9PgBlnM9sYIj2kVoZbPw-x9DDMekP2JQ&dib_tag=se&keywords=keyboard&qid=1708382782&sprefix=keyboar%2Caps%2C148&sr=8-5&th=1");
        Dialog.show("Product Info", p.toString(), "OK", null);
    }
    
}
