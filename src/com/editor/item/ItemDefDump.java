/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.editor.item;

import com.alex.loaders.items.ItemDefinitions;
import com.alex.store.Store;
import com.alex.utils.Utils;
import com.editor.Main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author Travis
 */
public class ItemDefDump {

    private static ItemDefinitions defs;
    private static Store STORE;

    public static void main(String args[]) {
        try {
            STORE = new Store("C:/Users/Jesse's/jagexcache/runescape/LIVE/");
        } catch (IOException ex) {
            System.out.println("Cannot find cache location");
        }
        if (Utils.getItemDefinitionsSize(STORE) > 30000) {
            for (int id = 0; id < Utils.getItemDefinitionsSize(STORE); id++) {
                defs = ItemDefinitions.getItemDefinition(STORE, id);
                dump();
               // Main.log("ItemDefDump", "Dumping Item "+defs.id);
            }
        } else {
            for (int id = 0; id < Utils.getItemDefinitionsSize(STORE); id++) {
                defs = ItemDefinitions.getItemDefinition(STORE, id);
                dump();
                //Main.log("ItemDefDump", "Dumping Item "+defs.id);
            }
        }

    }
    
    public static void editorDump(String cache) {
        try {
            STORE = new Store(cache);
        } catch (IOException ex) {
            Main.log("ItemDefDump", "Cannot find cache location");
        }
        if (Utils.getItemDefinitionsSize(STORE) > 30000) {
            for (int id = 0; id < Utils.getItemDefinitionsSize(STORE); id++) {
                defs = ItemDefinitions.getItemDefinition(STORE, id);
                dump();
               // Main.log("ItemDefDump", "Dumping Item "+defs.id);
            }
        } else {
            for (int id = 0; id < Utils.getItemDefinitionsSize(STORE); id++) {
                defs = ItemDefinitions.getItemDefinition(STORE, id);
                dump();
                //Main.log("ItemDefDump", "Dumping Item "+defs.id);
            }
        }

    }

    public static void dump() {
        File f = new File(System.getProperty("user.home") + "/desktop/886/886 Item Definitions/");
        f.mkdirs();
        String lineSep = System.getProperty("line.separator");
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(System.getProperty("user.home") + "/desktop/886/886 Item Definitions/" + defs.id + ".txt"), "utf-8"));
            writer.write("name = " + defs.getName());
            writer.write(lineSep);
            writer.write("value = " + defs.getValue());
            writer.write(lineSep);
            writer.write("members only = " + String.valueOf(defs.isMembersObject()));
            writer.write(lineSep);
            writer.write("stack ids = " + getStackIDs());
            writer.write(lineSep);
            writer.write("stack amounts = " + getStackAmts());
            writer.write(lineSep);
            writer.write("stackable = " + defs.getStackable());
            writer.write(lineSep);
            writer.write("inv model zoom = " + defs.getSpriteScale());
            writer.write(lineSep);
            writer.write("model rotation 1 = " + defs.getSpritePitch());
            writer.write(lineSep);
            writer.write("model rotation 2 = " + defs.getSpriteCameraRoll());
            writer.write(lineSep);
            writer.write("model offset 1 = " + defs.getSpriteTranslateX());
            writer.write(lineSep);
            writer.write("model offset 2 = " + defs.getSpriteTranslateY());
            writer.write(lineSep);
            writer.write("inv model id = " + defs.getModelId());
            writer.write(lineSep);
            writer.write("male equip model id 1 = " + defs.getPrimaryMaleModel());
            writer.write(lineSep);
            writer.write("female equip model id 1 = " + defs.getPrimaryFemaleModel());
            writer.write(lineSep);
            writer.write("male equip model id 2 = " + defs.getSecondaryMaleModel());
            writer.write(lineSep);
            writer.write("female equip model id 2 = " + defs.getSecondaryFemaleModel());
            writer.write(lineSep);
            writer.write("male equip model id 3 = " + defs.getTertiaryMaleEquipmentModel());
            writer.write(lineSep);
            writer.write("female equip model id 3 = " + defs.getTertiaryFemaleEquipmentModel());
            writer.write(lineSep);
            writer.write("inventory options = " + getInventoryOpts());
            writer.write(lineSep);
            writer.write("ground options = " + getGroundOpts());
            writer.write(lineSep);
            writer.write("changed model colors = " + getChangedModelColors());
            writer.write(lineSep);
            writer.write("changed texture colors = " + getChangedTextureColors());
            writer.write(lineSep);
            writer.write("shiftClickIndex = " + defs.shiftClickIndex);
            writer.write(lineSep);
            writer.write("searchable = " + defs.searchable);
            writer.write(lineSep);
            writer.write("noted item id = " + defs.certTemplateID);
            writer.write(lineSep);
            writer.write("tertiaryMaleEquipmentModel = " + defs.tertiaryMaleEquipmentModel);
            writer.write(lineSep);
            writer.write("tertiaryFemaleEquipmentModel = " + defs.tertiaryFemaleEquipmentModel);
            writer.write(lineSep);
            writer.write("primaryMaleHeadPiece = " + defs.primaryMaleHeadPiece);
            writer.write(lineSep);
            writer.write("primaryFemaleHeadPiece = " + defs.primaryFemaleHeadPiece);
            writer.write(lineSep);
            writer.write("primaryFemaleHeadPiece = " + defs.secondaryMaleHeadPiece);
            writer.write(lineSep);
            writer.write("secondaryFemaleHeadPiece = " + defs.secondaryFemaleHeadPiece);
            writer.write(lineSep);
            writer.write("spriteCameraYaw = " + defs.spriteCameraYaw);
            writer.write(lineSep);
            writer.write("category = "+ defs.category);
            writer.write(lineSep);
            writer.write("groundScaleX = " + defs.groundScaleX);
            writer.write(lineSep);
            writer.write("groundScaleY = " + defs.groundScaleY);
            writer.write(lineSep);
            writer.write("groundScaleZ = " + defs.groundScaleZ);
            writer.write(lineSep);
            writer.write("ambience = " + defs.ambience);
            writer.write(lineSep);
            writer.write("diffusion = " + defs.diffusion);
            writer.write(lineSep);
            writer.write("team = "+ defs.team);
            writer.write(lineSep);
            writer.write("unnotedId = "+ defs.unnotedId);
            writer.write(lineSep);
            writer.write("notedId = "+ defs.notedId);
            writer.write(lineSep);
            writer.write("placeHolderId = "+ defs.placeHolderId);
            writer.write(lineSep);
            writer.write("placeHolderTemplateId = "+ defs.placeHolderTemplateId);
            writer.write(lineSep);

            writer.write("params");
            writer.write(lineSep);
            if (defs.params != null) {
                for (int key : defs.params.keySet()) {
                    Object value = defs.params.get(key);
                    writer.write("KEY: " + key + ", VALUE: " + value);
                    writer.write(lineSep);
                }
            }
        } catch (IOException ex) {
            Main.log("ItemEditor", "Failed to export Item Defs to .txt");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getInventoryOpts() {
        String text = "";
        for (String option : defs.getItemActions()) {
            text += (option == null ? "null" : option) + ";";
        }
        return text;
    }

    public static String getGroundOpts() {
        String text = "";
        for (String option : defs.getGroundActions()) {
            text += (option == null ? "null" : option) + ";";
        }
        return text;
    }

    public static  String getChangedModelColors() {
        String text = "";
        if (defs.originalModelColors != null) {
            for (int i = 0; i < defs.originalModelColors.length; i++) {
                text += defs.originalModelColors[i] + "=" + defs.modifiedModelColors[i] + ";";
            }
        }
        return text;
    }

    public static String getChangedTextureColors() {
        String text = "";
        if (defs.originalTextureColors != null) {
            for (int i = 0; i < defs.originalTextureColors.length; i++) {
                text += defs.originalTextureColors[i] + "=" + defs.modifiedTextureColors[i] + ";";
            }
        }
        return text;
    }
    public static  String getChangedoldModelColors() {
        String text = "";
        if (defs.oldoriginalModelColors != null) {
            for (int i = 0; i < defs.oldoriginalModelColors.length; i++) {
                text += defs.oldoriginalModelColors[i] + "=" + defs.oldmodifiedModelColors[i] + ";";
            }
        }
        return text;
    }

    public static String getChangedoldTextureColors() {
        String text = "";
        if (defs.oldoriginalTextureColors != null) {
            for (int i = 0; i < defs.oldoriginalTextureColors.length; i++) {
                text += defs.oldoriginalTextureColors[i] + "=" + defs.oldmodifiedTextureColors[i] + ";";
            }
        }
        return text;
    }
    public static String getStackIDs() {
        String text = "";
        try {
            for (int index : defs.getStackIDs()) {
                text += index + ";";
            }
        } catch (Exception e) {
        }
        return text;
    }

    public static String getClientScripts() {
        String text = "";
        String lineSep = System.getProperty("line.separator");
        if (defs.params != null) {
            for (int key : defs.params.keySet()) {
                Object value = defs.params.get(key);
                text += "KEY: " + key + ", VALUE: " + value;
                text += lineSep;
            }
        }
        return text;
    }

    public static String getStackAmts() {
        String text = "";
        try {
            for (int index : defs.getStackAmounts()) {
                text += index + ";";
            }
        } catch (Exception e) {
        }
        return text;
    }

    public static String getUnknownArray1() {
        String text = "";
        try {
            for (int index : defs.unknownArray1) {
                text += index + ";";
            }
        } catch (Exception e) {
        }
        return text;
    }

    public static String getUnknownArray2() {
        String text = "";
        try {
            for (int index : defs.unknownArray2) {
                text += index + ";";
            }
        } catch (Exception e) {
        }
        return text;
    }

    public static String getUnknownArray3() {
        String text = "";
        try {
            for (int index : defs.unknownArray3) {
                text += index + ";";
            }
        } catch (Exception e) {
        }
        return text;
    }

    public static String getUnknownArray4() {
        String text = "";
        try {
            for (int index : defs.unknownArray4) {
                text += index + ";";
            }
        } catch (Exception e) {
        }
        return text;
    }

    public static String getUnknownArray5() {
        String text = "";
        try {
            for (int index : defs.unknownArray5) {
                text += index + ";";
            }
        } catch (Exception e) {
        }
        return text;
    }

    public static String getUnknownArray6() {
        String text = "";
        try {
            for (int index : defs.unknownArray6) {
                text += index + ";";
            }
        } catch (Exception e) {
        }
        return text;
    }
}
